package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Serializer {

    @SuppressWarnings("unchecked")
    public static byte[] itemStacksToString(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack itemStack : items) {
                dataOutput.writeObject(itemStack);
            }

            dataOutput.close();
            return outputStream.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[] {};
    }

}
