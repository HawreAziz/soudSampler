#!/usr/bin/env python

import struct
from serial import Serial
from config_class import *
import thread, os, time, tkMessageBox
from gui import Usart_GUI
from Tkinter import *
from sinc_kernel import *


class SerialComm():
    def __init__(self):
        root = Tk()
        self.gui = Usart_GUI(root)
        self.gui.window.protocol("WM_DELETE_WINDOW", self.on_close)
        self.ser = None
        self.button_bindings()
        self.change_slide_state(DISABLED)
        self.gui.change_echo_widget_state(DISABLED)

        self.ser = None
        self.init_serial()
        root.mainloop()



    def on_close(self):
        self.stop(Event)
        os._exit(0)


    def button_bindings(self):
        self.gui.start_btn.bind(LEFT_CLICK, self.start)
        self.gui.stop_btn.bind(LEFT_CLICK, self.stop)
        self.gui.on.bind(LEFT_CLICK, self.on)
        self.gui.off.bind(LEFT_CLICK, self.off)
        self.gui.reverb.bind(LEFT_CLICK, self.reverb)
        self.gui.record_btn.bind(LEFT_CLICK, self.save)
        self.gui.playback_btn.bind(LEFT_CLICK, self.load)
        self.set_slide_and_entry_bindings()


    def set_slide_and_entry_bindings(self):
        g = self.gui
        g.intensity_slide.bind(RELEASE, lambda event: self.send_echo_value(event, "Intensity"))
        g.length_slide.bind(RELEASE, lambda event: self.send_echo_value(event, "Length"))
        for ent in [g.bass_slide, g.middle_slide, g.treble_slide, g.treble_from, g.treble_to]:
            key_command = None
            if isinstance(ent, Scale):
                key_command = RELEASE
            else:
                key_command = ENTER
            ent.bind(key_command, self.send_control_value)





    def send_echo_value(self, event, _str):
        if self.serial_open() and self.gui.on[STATE] == DEACTIVATED:
            self.send_byte(_str)

    def send_control_value(self, event):
        self.send_control()


    def send_control(self):
        if self.serial_open():
            bytes= self.to_byte("Equalizer")
            self.cust_write(bytes)
            (b, m, t, f, _t) = self.gui.get_control_values()
            kernel = impulse_response(f, _t, b, m, t)
            list = kernel.total_filter_kernel()
            print(list)
            byte = struct.pack("<" + "f" * len(list), *list)
            self.cust_write(byte)
            self.get_output(TEXT)


    def on(self, event):
        if self.serial_open():
            self.send_byte(self.gui.on[TEXT])
            self.gui.button_settings(False, self.gui.on, self.gui.off)
            self.gui.change_echo_widget_state(NORMAL)


    def off(self, event):
        if self.serial_open():
            self.send_byte(self.gui.off[TEXT])
            self.gui.button_settings(True, self.gui.on, self.gui.off)
            self.gui.reverb.config(text=widget_name[0][3])
            self.gui.reset_echo_slides()
            self.gui.change_echo_widget_state(DISABLED)


    def reverb(self, event):
        if self.serial_open() and not self.gui.widget_not_disabled(self.gui.on):
            self.gui.change_button_text(self.gui.reverb)
            self.send_byte(button_text[self.gui.reverb[TEXT]])

    def check_blink(self):
        if self.gui.record_btn[TEXT] == widget_name[2][2]:
            self.gui.is_running = True
        else:
            self.gui.is_running = False


    def save(self, event):
        if self.serial_open() and self.gui.playback_btn[TEXT] == widget_name[2][3]:
            self.gui.change_button_text(self.gui.record_btn)
            self.send_byte(button_text[self.gui.record_btn[TEXT]])
            self.check_blink()

    def load(self, event):
        if not self.serial_open():
            self.gui.change_button_text(self.gui.playback_btn)
            self.serial_handler(OPEN)
            self.send_byte(button_text[self.gui.playback_btn[TEXT]])
        elif self.serial_open() and self.gui.widget_not_disabled(self.gui.start_btn):
            self.gui.change_button_text(self.gui.playback_btn)
            self.send_byte(button_text[self.gui.playback_btn[TEXT]])
            time.sleep(0.1)
            self.serial_handler(CLOSE)


    def serial_handler(self, state):
        try:
            if state == OPEN:
                self.ser.open()
            elif state == CLOSE:
                self.ser.close()
                self.disable_all_widgets()
            elif state == F_INPUT:
                self.ser.flushInput()
            elif state == F_OUTPUT:
                self.ser.flushOutput()
        except Exception, e:
            self.disable_all_widgets()
            self.display_err(e)


    def serial_open(self):
        if self.ser == None:
            return False
        else:
            return self.serial_is_open()

    def serial_is_open(self):
        try:
            return self.ser.isOpen()
        except Exception, e:
            self.disable_all_widgets()
            self.display_err(e)
            return False


    def display_err(self, e):
        self.gui.insert_into_log("Error: " + str(e))


    def start(self, event):
        if self.gui.widget_not_disabled(self.gui.start_btn) and not self.serial_open():
            self.serial_handler(OPEN)
            self.change_slide_state(NORMAL)
            self.gui.button_settings(False, self.gui.start_btn, self.gui.stop_btn)
            self.send_byte(self.gui.start_btn[TEXT])
            self.send_control()


    def stop(self, event):
        if self.gui.widget_not_disabled(self.gui.stop_btn) and self.serial_open():
            self.gui.button_settings(True, self.gui.start_btn, self.gui.stop_btn)                   ### remove if statement, and put these 2 line in try
            self.gui.button_settings(True, self.gui.on, self.gui.off)
            self.gui.reset_entries()
            self.gui.set_slide_values()
            self.gui.reset_echo_slides()
            try:
                self.send_byte(widget_name[1][2])
            except Exception, e:
                self.gui.insert_into_log("Error: connection lost. Connect to MD407 and restart")
            finally:
                self.serial_handler(CLOSE)

    # stops serial-communication
    def disable_all_widgets(self):
        self.gui.button_settings(True, self.gui.start_btn, self.gui.stop_btn)
        self.gui.button_settings(True, self.gui.on, self.gui.off)
        self.gui.reverb.config(state=DISABLED)
        self.change_slide_state(DISABLED)
        self.gui.change_echo_widget_state(DISABLED)
        self.gui.is_running = False
        self.gui.record_btn.config(text=widget_name[2][1])
        self.gui.reverb.config(text=widget_name[0][3])

    def init_serial(self):
        text = None
        try:
            self.ser = Serial()
            self.ser.port = serial_port
            self.ser.baudrate = 115200
            text = "Ready for communication"
        except Exception, e:
            text = "Error: %s" % str(e) + " " + "\n############ Restart ############"
        finally:
            self.disable_all_widgets()
            self.gui.insert_into_log(text)

    def get_code(self, hex_code, text):
        if hex_code == "0x55":
            if text == widget_name[2][1]:
                return "0x2d"
            elif text == widget_name[2][3]:
                return "0x7d"
            else:
                return hex_code
        else:
            return hex_code


    def get_output(self, text):
            try:
                resp = self.ser.read(1)
                code = self.get_code(hex(ord(resp)), text)
                self.gui.insert_into_log(get_output[code])
            except Exception, e:

                self.gui.insert_into_log("Error: got %s" % str(e.message) + "\n###Restart!###")

    def change_slide_state(self, state):
        g = self.gui
        list = [g.bass_slide, g.middle_slide, g.treble_slide, g.treble_from, g.treble_to]
        for slide in list:
            slide.config(state=state)


    # This function converts string to hexadecimal
    def send_byte(self, text):
        byte = self.to_byte(text)
        self.serial_handler(F_INPUT)
        self.serial_handler(F_OUTPUT)
        self.cust_write(byte)
        self.get_output(text)





    def cust_write(self, byte):
        try:
            for b in byte:
                self.ser.write(b)
                time.sleep(0.01)
        except Exception, e:
            self.disable_all_widgets()
            self.display_err(e)




    def to_byte(self, text):
        if widget_id[text][TYPE] == "0x4":
            byte = struct.pack("<cchif", chr(eval(access_code)), chr(eval(widget_id[text][TYPE])),
                               (int(widget_id[text][LENGTH]) + int(widget_id[text][LENGTH])),
                               int(self.gui.length_slide.get()), float(self.gui.intensity_slide.get()))

        elif widget_id[text][TYPE] == "0x5":
            byte = struct.pack("<cch", chr(eval(access_code)), chr(eval(widget_id[text][TYPE])), int(200))

        else:
            byte = struct.pack("<cchc", chr(eval(access_code)), chr(eval(widget_id[text][TYPE])),
                        int(widget_id[text][LENGTH]), chr(eval(widget_id[text][MESSAGE])))
        return byte


if __name__ == "__main__":
    serial = SerialComm()