package com.example.testmod.network;


import com.example.testmod.capabilities.magic.PlayerSyncedData;
import com.example.testmod.player.ClientMagicData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientBoundSyncPlayerData {
    PlayerSyncedData playerSyncedData;

    public ClientBoundSyncPlayerData(PlayerSyncedData playerSyncedData) {
        this.playerSyncedData = playerSyncedData;
    }

    public ClientBoundSyncPlayerData(FriendlyByteBuf buf) {
        playerSyncedData = new PlayerSyncedData(buf.readInt());
        playerSyncedData.setHasEvasion(buf.readBoolean());
        playerSyncedData.setHasAngelWings(buf.readBoolean());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerSyncedData.getServerPlayerId());
        buf.writeBoolean(playerSyncedData.getHasEvasion());
        buf.writeBoolean(playerSyncedData.getHasAngelWings());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            ClientMagicData.handlePlayerSyncedData(playerSyncedData);
        });

        return true;
    }
}