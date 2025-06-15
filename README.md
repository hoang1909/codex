# 🌳 TreeEventPlugin

**TreeEventPlugin** is a lightweight Spigot plugin for a collaborative **tree-watering event** system. Each player tracks their own progress and levels up their "tree", earning random rewards.

---

## 🚀 Features

- 🌱 Water a shared event tree using a special item
- 📈 Each player has personal watering progress and tree level
- 🎁 Randomized rewards based on tree level
- 🏆 Leaderboard based on personal tree level
- 🧾 Custom EXP requirements and reward tables via YAML
- 🔄 Event can be reset or ended with admin commands
- 📊 PlaceholderAPI support: `%treeevent_level%`

---

## 🔧 Building the Plugin

This project uses **Maven**.

To build the JAR file:

```bash
mvn -U clean package
```

> 📝 Use `-U` to force update snapshot dependencies like PlaceholderAPI.

The compiled JAR will be in:

```
target/TreeEventPlugin-1.0-SNAPSHOT.jar
```

Place it into your Minecraft server's `plugins/` folder.

---

## 📚 Usage

1. Start your server to generate default config files.
2. Configure:
   - `exp_levels.yml` – EXP requirements per level
   - `rewards.yml` – item rewards per level
   - `config.yml` – visual decoration for the shared tree
3. Give players a **Water Bucket** renamed to `"Water Bucket"` (can customize).
4. Place a **fixed ArmorStand** at the event area to act as the shared tre
