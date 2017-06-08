import sys
import tkFont


if sys.platform == "win32":
    serial_port = "COM5"
    width = 1112
    height = 560
    btn_width = 3
    btn_height = 2
    #xpadding =
else:
    serial_port = "/dev/ttyUSB0"
    width = 1700
    height = 1000
    btn_width = 2
    btn_height = 2
    xpadding = (250, 0)

list = ["Type of message", "Length of message"]


main_frame_height = 300
effect_width = 400
effect_height = 300
sample_width =  300
sample_height = 300
control_width =  400
control_height = 300


port = 9600
access_code = "0x55"

blink_color = "red"
blink_default = "grey"
right_image_path = "images/right.jpg"
left_image_path ="images/left.jpg"


def getFont():
    return tkFont.Font(family="helvetica", size=12)


LEFT_CLICK = "<Button-1>"
RELEASE = "<ButtonRelease-1>"
ENTER = "<Return>"
STATE = "state"
TEXT = "text"
DEACTIVATED = "disabled"
STOP = "stop"
TYPE = "type"
LENGTH = "length"
MESSAGE = "message"
OPEN = "open"
CLOSE = "close"
F_OUTPUT = "flushout"
F_INPUT = "flushinput"
S_READ = "read"
S_WRITE = "write"


widget_id = {
    "Start":                              {"type":"0x1", "length":"1", "message":"0x1"},
    "Stop":                               {"type":"0x1", "length":"1", "message":"0x0"},
    "Start\nRecording":                   {"type":"0x2", "length":"1", "message":"0x1"},
    "Stop\nRecording":                    {"type":"0x2", "length":"1", "message":"0x2"},
    "Start\nPlayback":                    {"type":"0x2", "length":"1", "message":"0x3"},
    "Stop\nPlayback":                     {"type":"0x2", "length":"1", "message":"0x4"},
    "ON":                                 {"type":"0x3", "length":"1", "message":"0x1"},
    "OFF":                                {"type":"0x3", "length":"1", "message":"0x0"},
    "REV-ON":                             {"type":"0x3", "length":"1", "message":"0x2"},
    "REV-OFF":                            {"type":"0x3", "length":"1", "message":"0x3"},
    "Intensity":                          {"type":"0x4", "length":"4"},
    "Length":                             {"type":"0x4", "length":"4"},
    "Equalizer":                          {"type":"0x5", "length":"4"},
}



# name of button/label/scales widgets
widget_name = [["Echo", "ON", "OFF", "REV-ON", "REV-OFF",  "Intensity", "Length"],                                              # name of all Echo-frame widgets
               ["Sample", "Start", "Stop"],                                                               # sample-frame widgets
               ["Record", "Start\nRecording", "Stop\nRecording", "Start\nPlayback", "Stop\nPlayback"],    # Record-control widgets
               ["Scale-Control", "Bass", "Middle", "Treble"],                                             # Scale-Control widgets
               ["Middle-From-To", "From: ", "To: "]]                                                      # Middle-From-To widgets

button_text = {
        "Stop\nRecording":"Start\nRecording",
        "Stop\nPlayback":"Start\nPlayback",
        "Start\nRecording":"Stop\nRecording",
        "Start\nPlayback":"Stop\nPlayback",
        "REV-ON":"REV-OFF",
        "REV-OFF":"REV-ON"
}


eq_tuple = ("700", "5000")

get_output = {
    "0x55": "Sampling started!",
    "0x4d": "Sampling stopped!",
    "0x2d": "Recording...",
    "0x6d": "Recording stopped!",
    "0x7d": "Started playing audio from SD-card!",
    "0x8d": "Stopped playing audio from SD-card!",
    "0x2f": "Echo ON",
    "0x4f": "Echo OFF",
    "0xbb": "Reverb ON",
    "0xcd": "Reverb OFF",
    "0xfe": "Intesity/Length changed successfully",
    "0xff": "Error: Could not change Intensity/Length",
    "0xad": "Equalizer changed successfully",
    "0x5f": "Error: Could not change equalizer settings"
}





