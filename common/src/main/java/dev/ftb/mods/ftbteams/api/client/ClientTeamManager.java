package dev.ftb.mods.ftbteams.api.client;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Used to track known teams and player on each client in the game. You can retrieve an instance of this via
 * {@link FTBTeamsAPI.API#getClientManager()}.
 */
public void initSelfDetails(UUID selfTeamID) {
   selfTeam = teamMap.get(selfTeamID);
   String username = Minecraft.getInstance().getUser().getGameProfile().getName();
   UUID userId = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
   selfKnownPlayer = knownPlayers.get(userId);
   FTBTeams.LOGGER.debug("Client userId: {}", userId);
   knownPlayers.forEach((uuid, player) -> {
      FTBTeams.LOGGER.debug("Known player UUID: {}, Name: {}", uuid, player.name);
   });
   if (selfKnownPlayer == null) {
      FTBTeams.LOGGER.warn("Local player id {} was not found in the known players list [{}]! FTB Teams will not be able to function correctly!",
            userId, String.join(",", knownPlayers.keySet().stream().map(UUID::toString).toList()));
   }
}
