package com.noteblockmelody.data;

import org.bukkit.Material;
import org.bukkit.Sound;

public record InstrumentNote(
    String name,         // 表示名（例: "C音"）
    Material material,   // GUI表示用アイテム（例: NOTE_BLOCK）
    float pitch,         // 再生ピッチ（例: 1.0f）
    Sound sound          // 再生サウンド（例: BLOCK_NOTE_BLOCK_GUITAR）
) {}