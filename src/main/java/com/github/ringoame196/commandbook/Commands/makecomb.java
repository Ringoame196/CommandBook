package com.github.ringoame196.commandbook.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class makecomb implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            // プレイヤーでない場合の処理
            commandSender.sendMessage(ChatColor.RED+"このコマンドはプレイヤーのみ実行できます");
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.isOp())
        {//op以外実行者させない
            player.sendMessage(ChatColor.RED+"あなたはこのコマンドを実行する権限を持っていません");
            return true;
        }

        if(strings.length==0)
        {//サブコマンド未入力
            player.sendMessage(ChatColor.RED+"使用方法:/makecomb <登録コマンド>");
            return true;
        }
        String Setcommand=strings[0].replace("/","");
        for(int i =1;i<strings.length;i++)
        {//サブコマンドすべてを 登録する
            Setcommand+=" "+strings[i];
        }
        //記入済みの本作成
        ItemStack commandbook = new ItemStack(Material.WRITTEN_BOOK);
        {
            BookMeta meta = (BookMeta) commandbook.getItemMeta();
            meta.setTitle(ChatColor.AQUA + "コマンドブック");
            meta.setAuthor(player.getName());
            meta.addPage("[登録コマンド]/" + Setcommand);
            commandbook.setItemMeta(meta);
        }
        player.getInventory().addItem(commandbook);

        player.sendMessage(ChatColor.YELLOW+"「"+Setcommand+"」のコマンドブックを発行しました");

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE,1,1);
        return true;
    }
}
