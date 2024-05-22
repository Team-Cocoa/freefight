package kr.teamcocoa.freefight.session.webhook;

import kr.teamcocoa.core.network.webhook.DiscordWebhook;

public class WebhookSender {

    private static DiscordWebhook getNewWebHook() {
        DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1242290862436909106/wcyIQ-XsyA4HUgA6TTG4bcikhgbuGLibVw1fnNssJ6eEvrSuuheh2EBEPvDvRR7GMTsT");
        discordWebhook.setAvatarUrl("https://cdn.discordapp.com/avatars/411055322795474944/9356625c8c4ae19ffe9805848562c0c7.png?size=256");
        discordWebhook.setTts(false);
        discordWebhook.setUsername("FreeFight Notificator");
        return discordWebhook;
    }




}
