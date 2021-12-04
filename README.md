# An Open Letter to RoboMWM

4 December 2021

Dear Robo,

Sorry for not using your only contact method - I don't use IRC any more, haven't for years. I was initially going to email you, however, you only use a noreply address, not even the GitHub redirect. I then tried to drop you a DM on Spigot, but your DMs are closed. I suppose I could have figured out what your GH redirect actually is based on your user ID, but I feel like I've already invested enough effort into contact attempts.

I was going to give you some forewarning before starting maintaining a fork, but since I can't, here we are. It's felt like I'm screaming into the void for a while doing issue management with no way of knowing when (or in the case of 1.17, if) a build will be released that actually fixes the problems. As I'm once again managing a small private server for friends, I was very much already intending to update GP myself if necessary. That said, I don't want to leave the GP community in the lurch while I laugh behind closed doors with the product of my work, so I intend to publish that work here.

I hope life is treating you well, there certainly are times when it's hard to make space for side projects like this. If you do come back to work on GP I will gladly continue to contribute upstream, but it feels somewhat futile to bother right now.

Regards and apologies,  
Adam

# GamerProtection - A GriefPrevention fork

Stop responding to grief and prevent it instead. GriefPrevention stops grief 
before it starts automatically without any effort from administrators, 
and with very little (self service) effort from players.
Because GriefPrevention teaches players for you, you won't have to publish a
training manual or tutorial for players, or add explanatory signs to your world.

> GriefPrevention is a Bukkit plugin, therefore it is compatible with any implemention of the Bukkit API. Implementations include CraftBukkit, Spigot, Paper, Tuinity, Purpur, etc.

- [Downloads](https://github.com/Easterlyn/GamerProtection/releases)
- [Dev Builds](https://ci.appveyor.com/project/Jikoo/gamerprotection) - Navigate to the Artifacts tab.

## Help and Support

Please do not seek support for GamerProtection in the GriefPrevention repository! Please use [Issues](https://github.com/Easterlyn/GamerProtection/issues) or [Discussions](https://github.com/Easterlyn/GamerProtection/discussions).

---

### Adding GamerProtection as a maven/gradle/etc. dependency

GamerProtection is available via [JitPack](https://jitpack.io/).
```xml
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
```

Replace `${gamerprotection.version}` number with the current version: [![](https://jitpack.io/v/Easterlyn/GamerProtection.svg)](https://jitpack.io/#Easterlyn/GamerProtection)
```xml
    <dependency>
      <groupId>com.github.Easterlyn</groupId>
      <artifactId>GamerProtection</artifactId>
      <version>${gamerprotection.version}</version>
      <scope>provided</scope>
    </dependency>
```

You can also add it to gradle/sbt/leiningen projects. For more information, see https://jitpack.io/#Easterlyn/GamerProtection/

---

[![Weird flex but ok](https://bstats.org/signatures/bukkit/GriefPrevention-legacy.svg)](https://bstats.org/plugin/bukkit/GriefPrevention-legacy)
(Plugin usage stats since version 16.11 - actual use across all versions is larger)
