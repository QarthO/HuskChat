/*
 * This file is part of HuskChat, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.william278.huskchat.bungeecord.event;

import net.md_5.bungee.api.ProxyServer;
import net.william278.huskchat.event.EventDispatcher;
import net.william278.huskchat.event.IBroadcastMessageEvent;
import net.william278.huskchat.event.IChatMessageEvent;
import net.william278.huskchat.event.IPrivateMessageEvent;
import net.william278.huskchat.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BungeeEventDispatcher implements EventDispatcher {
    private final ProxyServer server;

    public BungeeEventDispatcher(ProxyServer server) {
        this.server = server;
    }

    // In order to keep compatibility with the Velocity implementation, the Bungee events also return CompletableFuture
    @Override
    public CompletableFuture<IChatMessageEvent> dispatchChatMessageEvent(@NotNull Player sender, @NotNull String message, @NotNull String channelId) {
        final CompletableFuture<IChatMessageEvent> completableFuture = new CompletableFuture<>();
        completableFuture.complete(server.getPluginManager().callEvent(new ChatMessageEvent(sender, message, channelId)));
        return completableFuture;
    }

    @Override
    public CompletableFuture<IPrivateMessageEvent> dispatchPrivateMessageEvent(@NotNull Player sender, @NotNull List<Player> receivers, @NotNull String message) {
        final CompletableFuture<IPrivateMessageEvent> completableFuture = new CompletableFuture<>();
        completableFuture.complete(server.getPluginManager().callEvent(new PrivateMessageEvent(sender, receivers, message)));
        return completableFuture;
    }

    @Override
    public CompletableFuture<IBroadcastMessageEvent> dispatchBroadcastMessageEvent(@NotNull Player sender, @NotNull String message) {
        final CompletableFuture<IBroadcastMessageEvent> completableFuture = new CompletableFuture<>();
        completableFuture.complete(server.getPluginManager().callEvent(new BroadcastMessageEvent(sender, message)));
        return completableFuture;
    }
}
