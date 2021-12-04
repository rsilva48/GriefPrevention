# An Open Letter to RoboMWM

4 December 2021

Dear Robo,

Sorry for not using your only contact method - I don't use IRC any more, haven't for years. I was initially going to email you, however, you only use a noreply address, not even the GitHub redirect. I then tried to drop you a DM on Spigot, but your DMs are closed. I suppose I could have figured out what your GH redirect actually is based on your user ID, but I feel like I've already invested enough effort into contact attempts.

I was going to give you some forewarning before starting maintaining a fork, but since I can't, here we are. It's felt like I'm screaming into the void for a while doing issue management with no way of knowing when (or in the case of 1.17, if) a build will be released that actually fixes the problems. As I'm once again managing a small private server for friends, I was very much already intending to update GP myself if necessary. That said, I don't want to leave the GP community in the lurch while I laugh behind closed doors with the product of my work, so I intend to publish that work here.

I hope life is treating you well, there certainly are times when it's hard to make space for side projects like this. If you do come back to work on GP I will gladly continue to contribute upstream, but it feels somewhat futile to bother right now.

Regards and apologies,  
Adam

# GriefPrevention - The self-service anti-griefing Bukkit plugin for Minecraft servers since 2011.

Stop responding to grief and prevent it instead. GriefPrevention stops grief 
before it starts automatically without any effort from administrators, 
and with very little (self service) effort from players.
Because GriefPrevention teaches players for you, you won't have to publish a
training manual or tutorial for players, or add explanatory signs to your world.

> GriefPrevention is a Bukkit plugin, therefore it is compatible with any implemention of the Bukkit API. Implementations include CraftBukkit, Spigot, Paper, Tuinity, Purpur, etc.

- [Downloads](https://dev.bukkit.org/projects/grief-prevention/files)
- [Release Notes](https://github.com/TechFortress/GriefPrevention/releases)

## Help+Support

- **[Documentation (The Manual)](https://docs.griefprevention.com)** - Check here first! This contains the list of commands, permissions, configurations, answers to common questions, etc.
- [Issue Tracker](https://github.com/TechFortress/GriefPrevention/issues) - For reporting bugs
- [Discussions](https://github.com/TechFortress/GriefPrevention/discussions) - For asking questions, suggesting new ideas/features, and general discussion

Community support and general discussion on GriefPrevention can be found at these sites:

- [GitHub Discussions](https://github.com/TechFortress/GriefPrevention/discussions)
- [#GriefPrevention chat channel on IRC or discord](https://griefprevention.com/chat)
- [Grief Prevention on dev.bukkit.org](https://dev.bukkit.org/projects/grief-prevention)
- [GriefPrevention on spigotmc.org](https://www.spigotmc.org/resources/griefprevention.1884/)

---

### Adding GriefPrevention as a maven/gradle/etc. dependency

GriefPrevention will be added to maven central soon - in the meantime, there's this neat thing called JitPack [![](https://jitpack.io/v/TechFortress/GriefPrevention.svg)](https://jitpack.io/#TechFortress/GriefPrevention) that makes a public maven repo for public Github repos on the fly.
According to it, this is all you need to do to add to your pom.xml:
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

Replace `<version>` number with this number: [![](https://jitpack.io/v/TechFortress/GriefPrevention.svg)](https://jitpack.io/#TechFortress/GriefPrevention)
```xml
	<dependency>
	    <groupId>com.github.TechFortress</groupId>
	    <artifactId>GriefPrevention</artifactId>
	    <version>16.17.1</version>
        <scope>provided</scope>
	</dependency>
```

You can also add it to gradle/sbt/leiningen projects: https://jitpack.io/#TechFortress/GriefPrevention/

---

[![Weird flex but ok](https://bstats.org/signatures/bukkit/GriefPrevention-legacy.svg)](https://bstats.org/plugin/bukkit/GriefPrevention-legacy)
(Plugin usage stats since version 16.11 - actual use across all versions is larger)
