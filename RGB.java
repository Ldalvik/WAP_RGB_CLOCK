package plotter.clock;

import plotter.Port;

public class RGB {
    private Port port;

    public RGB(Port port){
        this.port = port;
    }

    public void send(String cmd){
        port.send(cmd + "/");
    }
}
