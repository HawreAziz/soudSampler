#!/usr/bin/env python

from  Tkinter import *
import time, threading
from config_class import *
from PIL import ImageTk, Image


class Usart_GUI:
    def __init__(self, root):
        self.window = root
        self.frame = Frame(self.window, bd=1, relief=SUNKEN, height=main_frame_height)
        self.frame.pack(side=TOP)
        self.frame2 = Frame(self.window, bd=1, relief=SUNKEN, height=main_frame_height)
        self.frame2.pack(side=TOP)
        self.create_frames()
        self.is_running = False
        self.vcmd = (self.window.register(self.validate), '%d', '%i', '%P', '%s', '%S', '%v', '%V', '%W')
        self.disable_frame_resizing()
        self.pack_frames()
        self.window.title("Serial Communication")
        self.screenWid = self.window.winfo_screenwidth()
        self.screenHeight = self.window.winfo_screenheight()
        self.set_size()
        self.create_log_field()
        self.button_config()
        thread = threading.Thread(target=self.set_blink, args=())
        thread.start()

    def button_config(self):
        self.stop_btn.config(state=DISABLED)
        self.off.config(state=DISABLED)
        #self.load_btn.config(state=DISABLED)

    def entry_values_allowed(self, event):
        _from = self.treble_from.get()
        to = self.treble_to.get()
        if _from and to:
            _from = int(_from)
            to = int(to)
            return to > 0 and 0 < _from <= 24000 and 24000 >= to >= _from
        else:
            return False


    def validate(self, action, index, value_if_allowed,
                 prior_value, text, validation_type, trigger_type, widget_name):
        if text in '0123456789':
            try:
                if not value_if_allowed:
                    return True
                float(value_if_allowed)
                return True
            except ValueError:
                return False
        else:
            return False

    def disable_frame_resizing(self):
        self.effect_frame.pack_propagate(0)
        self.sample_frame.pack_propagate(0)
        self.control_frame.pack_propagate(0)

    def create_frames(self):
        self.effect_frame = Frame(self.frame, width=effect_width, height=effect_height, bd=1, relief=SUNKEN)
        self.sample_frame = Frame(self.frame, width=sample_width, height=sample_height, bd=1, relief=SUNKEN)
        self.control_frame = Frame(self.frame, width=control_width, height=control_height, bd=1, relief=SUNKEN)

    def pack_frames(self):
        self.effect_frame.pack(side=LEFT, padx=(2,2))
        self.sample_frame.pack(side=LEFT, padx=(2,2))
        self.control_frame.pack(side=LEFT, padx=(2,2))
        self.create_effect_widgets()
        self.create_sample_widgets()
        self.create_control_widgets()


    ################################## Middle-From-To-widgets #############################
    def create_control_widgets(self):
        self.create_control_slides()
        self.create_control_entries()


    def create_control_entries(self):
        frame = LabelFrame(self.control_frame, text=widget_name[4][0])
        frame.pack_propagate(0)
        frame.pack(fill=BOTH, expand=True, padx=5)
        self.create_entries(frame)

    def create_entries(self, frame):
        self.treble_from = Entry(frame, width=10, validate='key', validatecommand=self.vcmd)
        self.treble_to = Entry(frame, width=10, validate='key', validatecommand=self.vcmd)
        from_label = Label(frame, text=widget_name[4][1])
        to_label = Label(frame, text=widget_name[4][2])
        from_label.grid(row=0, column=0, pady=10)
        self.treble_from.grid(row=0, column=1)
        to_label.grid(row=0, column=2, padx=(50, 0))
        self.treble_to.grid(row=0, column=3)

    ############################################################################



    ####################################  Scale-Control-widgets   ###################

    def create_control_slides(self):
        frame = LabelFrame(self.control_frame, text=widget_name[3][0], width=control_width)
        frame.pack_propagate(0)
        frame.pack()
        self.bass_slide = Scale(frame, label=widget_name[3][1], to=0, from_=4, resolution=0.1, length=200)
        self.middle_slide = Scale(frame, label=widget_name[3][2], to=0, from_=4, resolution=0.1, length=200)
        self.treble_slide = Scale(frame, label=widget_name[3][3], to=0, from_=4, resolution=0.1, length=200)
        self.set_slide_values()
        self.bass_slide.grid(row=0, padx=(90,0))
        self.middle_slide.grid(row=0, column=1, padx=(10,0))
        self.treble_slide.grid(row=0, column=2, padx=(0,23))

    def set_slide_values(self):
        for slide in [self.bass_slide, self.middle_slide, self.treble_slide]:
            slide.set("0.7")


    def reset_entries(self):
        self.treble_from.delete(0, END)
        self.treble_to.delete(0, END)


    def reset_echo_slides(self):
        self.intensity_slide.set("0.0")
        self.length_slide.set("2")


    def get_control_values(self):
        (_from, to) = (self.treble_from.get(), self.treble_to.get())
        if not _from:
            to = _from
            if not to:
                _from, to = eq_tuple
        if not to:
            _from = to
            if not _from:
                _from, to = eq_tuple
        return self.bass_slide.get(), self.middle_slide.get(), self.treble_slide.get(), _from, to

    ################################################################################



    ##################################### Sample/Record widgets ###########################
    def change_button_text(self, btn):
        btn.config(text=button_text[btn[TEXT]])

    def create_sample_widgets(self):
        self.sample_label = LabelFrame(self.sample_frame, text=widget_name[1][0], width=sample_width, height= 140, labelanchor=N, font=getFont())
        self.sample_label.pack_propagate(0)
        self.sample_label.pack(pady=15)
        self.record_label = LabelFrame(self.sample_frame, text=widget_name[2][0], width=sample_width, height=140, labelanchor=N, font=getFont())
        self.record_label.pack_propagate(0)
        self.record_label.pack(pady=15)
        self.blink_label = Label(self.record_label, text="R", width=1, height=1, bg=blink_default, bd=1, relief=SUNKEN, font=getFont())
        self.blink_label.grid(row=0, column=0)
        self.start_btn = self.create_record_widgets(self.sample_label, widget_name[1][1], 0, (50, 5))
        self.stop_btn = self.create_record_widgets(self.sample_label, widget_name[1][2], 1, (5,50))
        self.record_btn = self.create_record_widgets(self.record_label, widget_name[2][1], 1, (40, 5))
        self.playback_btn = self.create_record_widgets(self.record_label, widget_name[2][3], 2, (5, 50))

    ###################################################################################


    ################################### Record-wdigets ###############################

    def create_record_widgets(self, frame, text, column, padx):
        btn = Button(frame, text=text, width=10, height=5)
        btn.grid(row=0, column=column, padx=padx, pady=(5,5))
        return btn


    def set_blink(self):
        while True:
            if self.is_running:
                time.sleep(1)
                self.blink_label.config(bg=blink_color)
                time.sleep(1)
                self.blink_label.config(bg=blink_default)


    #########################################################################


    ############################# Effect widgets  ###########################
    def create_effect_widgets(self):
        self.effect_label = LabelFrame(self.effect_frame, text=widget_name[0][0], width=effect_width, height=effect_height, labelanchor=N, font=getFont())
        self.effect_label.pack_propagate(0)
        self.effect_label.pack()
        self.create_effect_btns()
        self.create_effect_slides()

    def create_effect_slides(self):
        frame = Frame(self.effect_label)
        frame.pack(side=LEFT, padx=80)
        self.intensity_slide = Scale(frame, to=0, from_=0.8, resolution=0.1, length=200)
        self.length_slide = Scale(frame, to_=2, from_=19, resolution=1, length=200)
        self.intensity_slide.grid(row=0, pady=10)
        self.length_slide.grid(row=0, column=1, pady=10, padx=(80,0))
        self.int_label = Label(frame, text=widget_name[0][5])
        self.int_label.grid(row=1, column=0 )
        self.len_label = Label(frame, text=widget_name[0][6])
        self.len_label.grid(row=1, column=1, padx=(80,0))


    def create_effect_btns(self):
        frame = Frame(self.effect_label)
        frame.pack(side=LEFT)
        self.on = Button(frame, text=widget_name[0][1], width=6, height=2)
        self.off = Button(frame, text=widget_name[0][2], width=6, height=2)
        self.reverb = Button(frame, text=widget_name[0][3], width=6, height=2)
        self.on.pack(side=TOP)
        self.off.pack(side=TOP)
        self.reverb.pack(side=TOP)
        self.reverb.config(state=DISABLED)

    def change_echo_widget_state(self, state):
        for widget in [self.intensity_slide, self.length_slide, self.reverb]:
            widget.config(state=state)

    ############################################################

    def create_image_labels(self):
        right = ImageTk.PhotoImage(Image.open(right_image_path))
        left = ImageTk.PhotoImage(Image.open(left_image_path))
        self.left_label = Label(self.frame2, image=left, width=300, height=250)
        self.right_label = Label(self.frame2, image=right, width=300, height=250)
        self.left_label.image = left
        self.right_label.image = right



    def create_log_field(self):
        self.log = Text(self.frame2, width=50, height=100, font=getFont())
        self.create_image_labels()
        self.left_label.pack(side=LEFT, padx=(0,20))
        self.log.pack(side=LEFT , padx=(0,20))
        self.right_label.pack(side=LEFT)
        self.insert_into_log("Log message here!")



    #activate/disable start and stop buttons
    def button_settings(self, activate_start, btn1, btn2):
        start_state = DISABLED
        stop_state = ACTIVE
        if activate_start:
            start_state = ACTIVE
            stop_state = DISABLED
        btn1.config(state=start_state)
        btn2.config(state=stop_state)
        if self.on in [btn1, btn2]:
            self.reverb.config(state=stop_state)

    #This function places the window in the middle of the screen, and make it unresizable
    def set_size(self):
        x = (self.screenWid / 2) - (width / 2)
        y = (self.screenHeight / 2) - (height / 2)
        self.window.geometry("%dx%d+%d+%d" % (width, height, x, y))
        self.window.resizable(False, False)


    def insert_into_log(self, text):
        self.log.config(state=NORMAL)
        self.log.delete("1.0", END)
        self.log.insert("1.0", text)
        self.log.config(state=DISABLED)


    def widget_not_disabled(self, btn):
        return btn[STATE] != DEACTIVATED



if __name__ == "__main__":
    root = Tk()
    usart = Usart_GUI(root)
    root.mainloop()

