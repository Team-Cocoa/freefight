package kr.teamcocoa.freefight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Serializer {

    @SuppressWarnings("unchecked")
    public static byte[] itemStacksToBytes(ItemStack[] items) {
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

    public static ItemStack[] bytesToItemStacks(byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            int length = dataInput.readInt();

            ItemStack[] itemStacks = new ItemStack[length];
            for (int i = 0; i < length; i++) {
                itemStacks[i] = ((ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return itemStacks;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ItemStack[] {};
    }

}
