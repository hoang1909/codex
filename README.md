# TreeEventPlugin

TreeEventPlugin is a simple Spigot plugin for a tree watering event.

## Building

This plugin uses Maven. To build the jar run:

```bash
mvn package
```

The resulting jar will be in `target/TreeEventPlugin-1.0-SNAPSHOT.jar` and can be
placed in your server's `plugins` folder.

## Usage

1. Start your server so the plugin can generate the default configuration files.
2. Edit `exp_levels.yml` and `rewards.yml` in the plugin folder to customise
   experience requirements and rewards.
3. Configure `config.yml` to adjust the decorative tree layers and effects.
4. Give players a Water Bucket named **"Water Bucket"** and place a fixed
   `ArmorStand` as the watering target.
5. Players right-click the stand with the bucket to gain EXP.
6. Use `/treeevent stats` to view your progress.
7. Admins can run `/treeevent reset` to clear data or `/treeevent end` to announce
   the event end.

If PlaceholderAPI is installed you can use `%treeevent_level%` to display a
player's current level.

### config.yml example

```yaml
tree-visual:
  enabled: true
  layers:
    - material: OAK_LOG
      y-offset: 0
    - material: OAK_LOG
      y-offset: 1
    - material: OAK_LEAVES
      y-offset: 2
  glow: false
  particle-effect:
    enabled: true
    type: VILLAGER_HAPPY
    interval-ticks: 200
```
