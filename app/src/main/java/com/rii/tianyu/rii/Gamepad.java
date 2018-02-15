package com.rii.tianyu.rii;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by tianyu on 15/02/2018.
 */

class Gamepad {
    short leftX;
    short leftY;
    short rightX;
    short rightY;
    byte z;
    byte rz;
    short buttons;
    byte id;
    DatagramSocket socket;
    InetAddress addr;

    public Gamepad(InetAddress addr) {
        this.id = (byte)255;
        this.addr = addr;
        connect();
    }

    public Gamepad(InetAddress addr, int id) {
        this.id = (byte)id;
        this.addr = addr;
        connect();
    }

    private void connect() {
        byte[] data = toByte();
        DatagramPacket packet = new DatagramPacket(data, data.length, addr, 6666);
        try {
            socket = new DatagramSocket();
            socket.send(packet);
            socket.receive(packet);
            byte id = packet.getData()[0];
            setId(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void send() {
        byte[] data = toByte();
        DatagramPacket packet = new DatagramPacket(data, data.length, addr, 6666);
        try {
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    byte[] toByte() {
        byte[] res = new byte[13];
        res[0] = (byte)(leftX & 0xff);
        res[1] = (byte)((leftX >> 8) & 0xff);
        res[2] = (byte)(leftY & 0xff);
        res[3] = (byte)((leftY >> 8) & 0xff);
        res[4] = (byte)(rightX & 0xff);
        res[5] = (byte)((rightX >> 8) & 0xff);
        res[6] = (byte)(rightY & 0xff);
        res[7] = (byte)((rightY >> 8) & 0xff);
        res[8] = z;
        res[9] = rz;
        res[10] = (byte)(buttons & 0xff);
        res[11] = (byte)((buttons >> 8) & 0xff);
        res[12] = id;
        return res;
    }

    void down(int idx) {
        buttons |= (1 << idx);
    }

    void up(int idx) {
        buttons &= ~(1 << idx);
    }

    void setId(int id) {
        this.id = (byte)id;
    }
    void setId(byte id) {
        this.id = id;
    }
}