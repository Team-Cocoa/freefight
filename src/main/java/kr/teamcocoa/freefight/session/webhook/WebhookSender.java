package kr.teamcocoa.freefight.session.webhook;

import kr.teamcocoa.core.network.webhook.DiscordWebhook;
import kr.teamcocoa.freefight.kits.Kits;
import kr.teamcocoa.freefight.player.FreeFightPlayer;
import kr.teamcocoa.freefight.session.FreeFightSession;

public class WebhookSender {

    public static DiscordWebhook getNewWebHook() {
        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1242290862436909106/wcyIQ-XsyA4HUgA6TTG4bcikhgbuGLibVw1fnNssJ6eEvrSuuheh2EBEPvDvRR7GMTsT");
        discordWebhook.setAvatarUrl("https://cdn.discordapp.com/avatars/411055322795474944/9356625c8c4ae19ffe9805848562c0c7.png?size=256");
        discordWebhook.setTts(false);
        discordWebhook.setUsername("FreeFight Notificator");
        return discordWebhook;
    }

    public static DiscordWebhook.EmbedObject getSessionStartWebhookEmbed(FreeFightSession freeFightSession) {
        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        embed.setTitle(freeFightSession.getId() + " has been started.");

        embed.addField("Kits", Kits.getNameByEnum(freeFightSession.getKits()), true);

        embed.addField("Player 1", "`" + freeFightSession.getFreeFightPlayer1().getPlayer().getName() + "`", true);
        embed.addField("Player 2", "`" + freeFightSession.getFreeFightPlayer2().getPlayer().getName() + "`", true);

        return embed;
    }

    public static DiscordWebhook.EmbedObject getSessionEndWebhookEmbed(FreeFightSession session, FreeFightPlayer winner, FreeFightPlayer loser) {
        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        embed.setTitle(session.getId() + " has been ended.");

        embed.addField("Kits", Kits.getNameByEnum(session.getKits()), true);

        if(winner == null) {
            embed.addField("Winner", "None", true);
            embed.addField("Loser", "None", true);
        }
        else {
            embed.addField("Winner", "`" + winner.getPlayer().getName() + "`", true);
            embed.addField("Loser", "`" + loser.getPlayer().getName() + "`", true);
        }

        return embed;
    }

}
