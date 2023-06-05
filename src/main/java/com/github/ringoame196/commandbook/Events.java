package com.github.ringoame196.commandbook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.md_5.bungee.api.chat.*;

public class Events implements Listener {
    @EventHandler
    public void onPlayerInteractEvent (PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (item != null && item.getType().equals(Material.WRITTEN_BOOK)) {
            BookMeta meta = (BookMeta) item.getItemMeta();
            //コマンドブックという名前以外だと実行しない
            if (!meta.getTitle().equals(ChatColor.AQUA + "コマンドブック")) {
                return;
            }

            //スニークしてなかったら処理をしない
            if (!player.isSneaking()) {
                return;
            }

            e.setCancelled(true);
            //opのみ実行可能にする
            if(!player.isOp())
            {
                player.sendMessage(ChatColor.RED+"コマンドブックはOPを持っていないと実行できません");
                return;
            }
            String command = meta.getPage(1).replace("[登録コマンド]/","");

            if(!meta.getAuthor().equals(player.getName()))
            {//著者と実行者が違うときの処理
                BaseComponent[] message = TextComponent.fromLegacyText(ChatColor.RED+"コマンド:/"+command+ ChatColor.YELLOW+" [実行]");
                ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+command);
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("安全の上実行してください").create());
                message[1].setClickEvent(clickEvent);
                message[1].setHoverEvent(hoverEvent);
                player.spigot().sendMessage(message);

                player.sendMessage(ChatColor.AQUA+"[保護機能]あなた以外が設定したものです。実行する場合は上のメッセージをクリック");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE,1,1);
                return;
            }
            //著者と実行者が同じだった処理
            Bukkit.dispatchCommand(player, command);
            player.sendMessage(ChatColor.GREEN+"コマンドを実行しました");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE,1,1);
        }
    }
}
