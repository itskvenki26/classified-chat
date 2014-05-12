package com.cs9033.classified.controllers;

import java.util.ArrayList;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class ClassifiedPacketListener implements PacketListener {
	public static final String TAG = "ClassifiedPacketListener";

	ArrayList<Packet> packetList = new ArrayList<Packet>();

	@Override
	public void processPacket(Packet packet) {
		if (packet instanceof Message) {
			Message m = (Message) packet;

			if (m.getType() == Message.Type.chat) {
				packetList.add(packet);
			}
		}
	}

	public Packet[] getPacketList() {
		Packet[] p = new Packet[0];
		p = packetList.toArray(p);
		packetList.clear();
		return p;
	}

}
