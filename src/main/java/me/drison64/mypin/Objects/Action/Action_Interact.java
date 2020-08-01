package me.drison64.mypin.Objects.Action;

import me.drison64.mypin.Main;
import me.drison64.mypin.Objects.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;

import java.util.List;

public class Action_Interact extends Action {

    private Main main;
    private String[] splitted;
    private int delay = 1;

    public Action_Interact(Main main, ActionType type) {
        super(main, type);
        this.main = main;
    }

    @Override
    public void run(List<String> data, Integer line, Block block, Player player) {
        this.splitted = data.get(line - 1).split(" ");
        if (!(splitted[1].isEmpty())) {
            try {
                delay = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Error occured at line: " + (line + 1) + ", value is not a integer");
            }
        }

        if (InteractEnum.DOOR.getMaterialList().contains(block.getType())) {

            Door door = (Door) block.getBlockData();

            door.setOpen(true);

            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run() {
                    door.setOpen(false);
                }
            }, delay * 20);

        }

        if (InteractEnum.TRAPDOOR.getMaterialList().contains(block.getType())) {

            TrapDoor trapDoor = (TrapDoor) block.getBlockData();

            trapDoor.setOpen(true);

            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run() {
                    trapDoor.setOpen(false);
                }
            }, delay * 20);

        }

        if (InteractEnum.BUTTON.getMaterialList().contains(block.getType())) {

            //TODO Button

        }

        if (InteractEnum.CHEST.getMaterialList().contains(block.getType())) {

            Chest chest = (Chest) block.getBlockData();

            player.openInventory(chest.getBlockInventory());

        }

        runNext(data, line, block, player);

    }

}
