package com.deeplake.exp1182.manasystem.client;

import com.deeplake.exp1182.manasystem.network.PacketGatherMana;
import com.deeplake.exp1182.setup.Messages;
import net.minecraftforge.client.event.InputEvent;

public class KeyInputHandler {

    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.gatherManaKeyMapping.consumeClick()) {
            Messages.sendToServer(new PacketGatherMana());
        }
    }
}
