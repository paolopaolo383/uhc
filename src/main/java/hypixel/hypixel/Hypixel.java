package hypixel.hypixel;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public final class Hypixel extends JavaPlugin implements Listener, CommandExecutor
{
    public boolean isneither = true;
    public int ti;
    public Location loc = null;
    public String neither = "uhcne";
    public String mapname = "uhc";
    public int umapid = 0;
    public int peacetime = 10;
    public int killtime = 35;
    public int deathmatchtime = 3;
    public int[] borderarea = {2000,105,5};
    private Scoreboard board;
    private Objective obj;
    private Score one;
    private Score two;
    private Score three;
    private Score four;
    private Score five;
    private Score six;
    private Score seven;
    int totaltick=0;
    boolean isdropinghead = false;
    boolean isgaming=false;
    String game="none";
    int min, sec, tick;
    ConsoleCommandSender consol = Bukkit.getConsoleSender();
    HashMap<UUID, Integer> hack = new HashMap<UUID, Integer>();
    HashMap<UUID, Integer> diamond = new HashMap<UUID, Integer>();
    HashMap<UUID, Integer> stone = new HashMap<UUID, Integer>();
    HashMap<UUID, Boolean> perunskill = new HashMap<UUID, Boolean>();
    @SuppressWarnings("deprecation")
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        consol.sendMessage( ChatColor.AQUA + "[Hypixel] 하이픽셀 플러그인 v1.1 활성화.");
        config();

        consol.sendMessage( ChatColor.AQUA + "[Hypixel] 레시피 제작중");


        ItemStack item = new ItemStack(Material.IRON_PICKAXE);
        item.addEnchantment(Enchantment.DIG_SPEED,1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        ShapedRecipe newrecipe = new ShapedRecipe(new NamespacedKey(this, "Quick Pick"),item).shape(new String[]{"@@@","P#P"," # "}).setIngredient('@',Material.IRON_ORE).setIngredient('#',Material.STICK).setIngredient('P',Material.COAL);
        getServer().addRecipe(newrecipe);


        item = new ItemStack(Material.IRON_INGOT,10);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "irons"),item).shape(new String[]{"@@@","@#@","@@@"}).setIngredient('@',Material.IRON_ORE).setIngredient('#',Material.COAL);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.GOLD_INGOT,10);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "golds"),item).shape(new String[]{"@@@","@#@","@@@"}).setIngredient('@',Material.GOLD_ORE).setIngredient('#',Material.COAL);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.IRON_SWORD,1);
        item.addEnchantment(Enchantment.DAMAGE_ALL,2);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Apprentice Sword"),item).shape(new String[]{" @ "," # "," @ "}).setIngredient('@',Material.REDSTONE_BLOCK).setIngredient('#',Material.IRON_SWORD);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.BOW,1);
        item.addEnchantment(Enchantment.ARROW_DAMAGE,2);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Apperntice Bow"),item).shape(new String[]{" @#","@ #"," @#"}).setIngredient('@',Material.REDSTONE_TORCH).setIngredient('#',Material.STRING);
        getServer().addRecipe(newrecipe);


        item = new ItemStack(Material.ENCHANTED_BOOK,1);
        EnchantmentStorageMeta a =(EnchantmentStorageMeta) item.getItemMeta();
        a.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        item.setItemMeta(a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "protection_book"),item).shape(new String[]{"   "," @@"," @#"}).setIngredient('@',Material.PAPER).setIngredient('#',Material.IRON_INGOT);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.ENCHANTED_BOOK,1);
        EnchantmentStorageMeta b =(EnchantmentStorageMeta) item.getItemMeta();
        b.addStoredEnchant(Enchantment.DAMAGE_ALL,1,false);
        item.setItemMeta(b);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "sharpness_book"),item).shape(new String[]{"Q  "," @@"," @#"}).setIngredient('@',Material.PAPER).setIngredient('Q',Material.FLINT).setIngredient('#',Material.IRON_SWORD);
        getServer().addRecipe(newrecipe);


        item = new ItemStack(Material.ANVIL,1);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Light Anvil"),item).shape(new String[]{"@@@"," ! ","@@@"}).setIngredient('@',Material.IRON_INGOT).setIngredient('!',Material.IRON_BLOCK);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.DIAMOND_HELMET,1);
        //item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE,1);
        //item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS,1);
        //item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE,1);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.displayName(Component.text("Tarnhelm"));
        a.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE,1);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE,1);
        item.setItemMeta((ItemMeta) a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Tarnhelm"),item).shape(new String[]{"@.@","@#@","   "}).setIngredient('.',Material.IRON_INGOT).setIngredient('#',Material.REDSTONE_BLOCK).setIngredient('@',Material.DIAMOND);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.IRON_HELMET,1);
        //item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS,1);
        //item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE,1);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        a.displayName(Component.text("Apprentice Helmet"));
        item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE,1);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS,1);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE,1);
        item.setItemMeta((ItemMeta) a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Apprentice Helmet"),item).shape(new String[]{"@@@","@#@","   "}).setIngredient('@',Material.IRON_INGOT).setIngredient('#',Material.REDSTONE_TORCH);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.DIAMOND_PICKAXE,2,(short) 1544);
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS,1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Philosopher's Pickaxe"),item).shape(new String[]{"$@$","Q#Q"," # "}).setIngredient('@',Material.GOLD_ORE).setIngredient('$',Material.IRON_ORE).setIngredient('Q',Material.LAPIS_BLOCK).setIngredient('#',Material.STICK);
        getServer().addRecipe(newrecipe);


        item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4,false);
        a.displayName(Component.text("Dragon Armor"));
        item.setItemMeta(a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Dragon Armor"),item).shape(new String[]{" @ "," # ","%*%"}).setIngredient('@',Material.MAGMA_CREAM).setIngredient('#',Material.DIAMOND_CHESTPLATE).setIngredient('%',Material.OBSIDIAN).setIngredient('*',Material.ANVIL);
        getServer().addRecipe(newrecipe);


        item = new ItemStack(Material.IRON_SWORD);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.addStoredEnchant(Enchantment.DAMAGE_ALL,2,false);
        a.displayName(Component.text("Anduril"));
        item.setItemMeta(a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Andeuril"),item).shape(new String[]{" @ "," # ","%*%"}).setIngredient('@',Material.MAGMA_CREAM).setIngredient('#',Material.DIAMOND_CHESTPLATE).setIngredient('%',Material.OBSIDIAN).setIngredient('*',Material.ANVIL);
        getServer().addRecipe(newrecipe);


        item = new ItemStack(Material.IRON_SWORD);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.addStoredEnchant(Enchantment.DAMAGE_ALL,2,false);
        a.displayName(Component.text("Anduril"));
        item.setItemMeta(a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Anduril"),item).shape(new String[]{" @ "," # ","%*%"}).setIngredient('@',Material.MAGMA_CREAM).setIngredient('#',Material.DIAMOND_CHESTPLATE).setIngredient('%',Material.OBSIDIAN).setIngredient('*',Material.ANVIL);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.GOLDEN_APPLE);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Light Apple"),item).shape(new String[]{" @ ","@#@"," @ "}).setIngredient('@',Material.GOLD_INGOT).setIngredient('#',Material.APPLE);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.ARROW, 20);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Arrow Economy"),item).shape(new String[]{"@@@","###","***"}).setIngredient('@',Material.FLINT).setIngredient('#',Material.STICK).setIngredient('*',Material.FEATHER);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.DIAMOND_SWORD);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.addStoredEnchant(Enchantment.DAMAGE_ALL,2,false);
        a.displayName(Component.text("Dragon Sword"));
        item.setItemMeta(a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Dragon Sword"),item).shape(new String[]{" @ "," # ","%@%"}).setIngredient('@',Material.BLAZE_POWDER).setIngredient('#',Material.DIAMOND_SWORD).setIngredient('%',Material.OBSIDIAN);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.PLAYER_HEAD);//
        SkullMeta aq = (SkullMeta) item.getItemMeta();
        aq.setOwner("Gold Steve Head");
        aq.displayName(Component.text("Golden Head"));
        item.setItemMeta(aq);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Golden Head"),item).shape(new String[]{"@@@","@#@","@@@"}).setIngredient('@',Material.GOLD_INGOT).setIngredient('#',Material.PLAYER_HEAD);
        getServer().addRecipe(newrecipe);

        item = new ItemStack(Material.DIAMOND_AXE);
        a =(EnchantmentStorageMeta) item.getItemMeta();
        a.displayName(Component.text("Axe of Perun"));

        item.setItemMeta(a);
        newrecipe = new ShapedRecipe(new NamespacedKey(this, "Axe of Perun"),item).shape(new String[]{"@$% ","@^ "," ^ "}).setIngredient('@',Material.DIAMOND).setIngredient('$',Material.TNT).setIngredient('%',Material.FIRE_CHARGE).setIngredient('^',Material.STICK);
        getServer().addRecipe(newrecipe);

        consol.sendMessage( ChatColor.GREEN + "[Hypixel] 레시피 제작완료");


        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(isgaming)
                {
                    if(min>-1&&sec>20)
                    {

                        List players = getServer().getWorld(mapname).getPlayers();
                        players.add(getServer().getWorld(neither).getPlayers());
                        int qqeq = players.size();
                        for(int i = 0;i<qqeq;i++)
                        {
                            Player pl = (Player) players.get(i);
                            uhcscboard(pl);
                        }
                    }

                    totaltick++;
                    tick++;
                    if (tick==20)
                    {
                        tick=0;
                        sec++;
                        List players = getServer().getWorld(mapname).getPlayers();
                        players.add(getServer().getWorld(neither).getPlayers());
                        int qqeq = players.size();
                        for(int i = 0;i<qqeq;i++)
                        {
                            Player pl = (Player) players.get(i);
                            if(pl.getInventory().getItemInMainHand().displayName().equals("Anduril"))
                            {
                                PotionEffect p = new PotionEffect(PotionEffectType.SPEED, 1, 1);
                                pl.addPotionEffect(p);
                                p = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1, 1);
                                pl.addPotionEffect(p);
                            }
                        }
                    }
                    if (sec==60)
                    {
                        sec=0;
                        min++;
                    }


                    if(min==peacetime&&sec==0&&tick==0)
                    {
                        getServer().getWorld(mapname).setPVP(true);
                        getServer().getWorld(neither).setPVP(true);
                        isdropinghead = true;
                        getServer().getWorld(mapname).getWorldBorder().setSize(borderarea[1],  killtime*60);
                        List play = getServer().getWorld(mapname).getPlayers();
                        play.add(getServer().getWorld(neither).getPlayers());
                        int q = play.size();
                        for(int i = 0;i<q;i++)
                        {
                            Player pl = (Player) play.get(i);
                            pl.sendTitle(ChatColor.YELLOW+"자기장이 줄어듭니다","평화시간 끝", 0,40,20);
                        }
                    }
                    if(min==(peacetime+killtime)&&sec==0&&tick==0)
                    {
                        isneither = false;
                        List play = getServer().getWorld(mapname).getPlayers();
                        play.add(getServer().getWorld(neither).getPlayers());
                        for (Object o : play) {
                            Player pl = (Player) o;
                            pl.sendTitle(ChatColor.RED + "자기장이 줄어듭니다", "  ", 0, 40, 20);
                            if (pl.getGameMode() == GameMode.SURVIVAL) {
                                pl.setGlowing(true);
                                Random random = new Random();
                                int x = random.nextInt(borderarea[1])-(borderarea[1]/2);
                                random = new Random();
                                int z = random.nextInt(borderarea[1])-(borderarea[1]/2);
                                int y = getServer().getWorld(mapname).getHighestBlockYAt(x, z)+1;

                                loc = new Location(getServer().getWorld(mapname), x, y, z);
                                loc.getBlock().setType(Material.BEDROCK);
                                pl.teleport(loc);
                            }
                        }
                        getServer().getWorld(mapname).getWorldBorder().setSize(borderarea[2], deathmatchtime*60);
                        //타이틀
                    }
                }
                else
                {

                    totaltick = 0;
                    //getServer().getWorld("world").getWorldBorder().setSize(3000);
                    tick=0;
                    sec=0;
                    min=0;

                }
            }

        }.runTaskTimer(this, 0L, 1L);
    }

    @EventHandler
    public void Onportal(PlayerPortalEvent e)
    {
        if(isneither)
            e.setCancelled(true);
    }
    @EventHandler
    public void OnDamage(EntityDamageByEntityEvent e)
    {
        if(e.getDamager().getType()==EntityType.PLAYER&&e.getEntity().getType()==EntityType.PLAYER)
        {
            Player hitter = (Player)e.getDamager();
            Player p = (Player)e.getEntity();

            if(hitter.getInventory().getItemInMainHand().displayName().equals("Axe of Perun"))
            {
                if(perunskill.get(hitter.getUniqueId()))
                {
                    perunskill.put(hitter.getUniqueId(),false);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            perunskill.put(hitter.getUniqueId(),true);
                            e.setDamage(e.getDamage()+1.5f);
                            Location locccccc = e.getEntity().getLocation();
                            World w = p.getWorld();
                            w.strikeLightningEffect(locccccc);
                            return;
                        }


                    }.runTaskTimer(this, 160L, 1L);
                }
            }

        }
    }
    public void uhcscboard(Player player)
    {
        int p = 1;
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        board = sm.getNewScoreboard();
        obj = board.registerNewObjective("totalplaytime", "dummy");
        obj.setDisplayName(ChatColor.AQUA +"UHC");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        one = obj.getScore(ChatColor.GREEN+String.valueOf(min)+":"+String.valueOf(sec));
        one.setScore(p);
        p++;
        two = obj.getScore(ChatColor.WHITE+"총 플레이 시간");
        two.setScore(p);
        p++;
        if(min<peacetime)
        {
            int ti = ((peacetime*60)-(totaltick/20));

            three = obj.getScore(ChatColor.GREEN+String.valueOf((int)(ti/60))+":"+String.valueOf((ti%60)));
        }
        else if(min<(peacetime+killtime))
        {
            int ti = (((peacetime+killtime)*60)-(totaltick/20));

            three = obj.getScore(ChatColor.GREEN+String.valueOf((int)(ti/60))+":"+String.valueOf((ti%60)));
        }
        else if(min<((peacetime+killtime+deathmatchtime)+10))
        {
            int ti = ((((peacetime+killtime+deathmatchtime)+10)*60)-(totaltick/20));

            three = obj.getScore(ChatColor.GREEN+String.valueOf((int)(ti/60))+":"+String.valueOf((ti%60)));
        }
        three.setScore(p);
        p++;
        if(min<peacetime)
        {
            four= obj.getScore(ChatColor.WHITE+"평화시간 끝나기");
        }
        else if(min<(peacetime+killtime))
        {
            four= obj.getScore(ChatColor.WHITE+"데스메치까지");
        }
        else if(min<((peacetime+killtime+deathmatchtime)+10))
        {
            four= obj.getScore(ChatColor.WHITE+"게임 끝까지");
        }
        four.setScore(p);
        p++;

        six = obj.getScore((min>=10?ChatColor.RED:ChatColor.GREEN)+String.valueOf(-1*(int)getServer().getWorld(mapname).getWorldBorder().getSize()/2)+" , "+String.valueOf((int)getServer().getWorld(mapname).getWorldBorder().getSize()/2));
        six.setScore(p);
        p++;

        seven = obj.getScore(ChatColor.WHITE+"자기장 좌표");
        seven.setScore(p);
        p++;

        int border = (int)getServer().getWorld(mapname).getWorldBorder().getSize()/2;
        int x = (player.getLocation().getBlockX()<0?-1:1)*player.getLocation().getBlockX();
        int z = (player.getLocation().getBlockZ()<0?-1:1)*player.getLocation().getBlockZ();
        if(x<z)
        {
            x = z;
        }
        x = border-x;
        six = obj.getScore(((x<100)?ChatColor.RED:ChatColor.GREEN)+String.valueOf(x));
        six.setScore(p);
        p++;

        player.setScoreboard(board);
    }





    @EventHandler
    public void leave(PlayerQuitEvent e)
    {

        e.setQuitMessage(getConfig().getString("서버퇴장"));
        if(e.getPlayer().getGameMode() == GameMode.SURVIVAL)
        {
            if(isgaming)
            {
                e.getPlayer().setHealth(0);
            }
        }
        if(isgaming)
        {
            Player Winner = null;
            int sur = 0;
            List players = getServer().getWorld(mapname).getPlayers();
            int top = players.size();
            for(int i = 0;i<top;i++)
            {
                Player pl = (Player) players.get(i);
                if(pl.getGameMode()==GameMode.SURVIVAL)
                {
                    sur++;
                    Winner = (Player) pl;

                }
            }
            if(sur==1)
            {
                setGame((Player)Winner);
            }
        }

    }
    @EventHandler
    public void dead(PlayerDeathEvent e)
    {
        Player player  = e.getEntity();
        getServer().sendMessage(Component.text(ChatColor.RED+"사람이 죽었다"));
        Location locccccc = e.getEntity().getLocation();
        World w = player.getWorld();
        w.strikeLightningEffect(locccccc);
        if(e.getEntity().getType()==EntityType.PLAYER)
        {
            if(isgaming)
            {

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setDisplayName(player.getName()+"'s head");
                skullMeta.setOwner(player.getName());
                skull.setItemMeta(skullMeta);
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(),skull).setVelocity(new org.bukkit.util.Vector(0,0,0));



                e.getEntity().setGameMode(GameMode.SPECTATOR);
                Player Winner = null;
                int sur = 0;
                List players = getServer().getWorld(mapname).getPlayers();
                int top = players.size();
                for(int i = 0;i<top;i++)
                {
                    Player pl = (Player) players.get(i);
                    if(pl.getGameMode()==GameMode.SURVIVAL)
                    {
                        sur++;
                        Winner = (Player) pl;
                    }
                }
                if(sur==1)
                {
                    setGame((Player)Winner);
                }
            }

        }


    }

    @EventHandler
    public void join(PlayerJoinEvent e)
    {

        consol.sendMessage( ChatColor.YELLOW + "[사람이 들어옴]");
        Player player = e.getPlayer();
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1000);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        UUID uuid = player.getUniqueId();
        perunskill.put(uuid,true);
        e.setJoinMessage(getConfig().getString("서버접속"));
        player.sendMessage(ChatColor.RED+"서버 소개\n"+ChatColor.WHITE+"공격 딜레이가 없습니다.\n/uhc명령어를 통해 게임을 시작할 수 있습니다.\n특별 레시피가 있습니다.\n레시피는 기본적으로 조합법을 드립니다.");
        if(isgaming)
        {
            e.getPlayer().teleport(getServer().getWorld(mapname).getHighestBlockAt(0, 0).getLocation().add(0,1,0));
            player.setGameMode(GameMode.SPECTATOR);
        }
        else
        {
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            e.getPlayer().teleport(getServer().getWorld("world").getHighestBlockAt(0, 0).getLocation().add(0,1,0));
        }

    }


    public void setGame(Player winner)
    {
        isgaming = false;
        totaltick = 0;
        tick = 0;
        sec = 0;
        min = 0;
        List<Player> players = getServer().getWorld(mapname).getPlayers();

        int top = players.size();
        for(int i = 0;i<top;i++)
        {
            Player pl = (Player) players.get(i);
            pl.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            pl.setGlowing(false);
            pl.resetTitle();
            pl.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            pl.sendTitle(winner.getName(),ChatColor.YELLOW+"승리!", 0,100,40);
            pl.teleport(getServer().getWorld("world").getHighestBlockAt(0, 0).getLocation().add(0,1,0));
            pl.setGameMode(GameMode.SURVIVAL);
        }
        winner.setGlowing(true);
    }/*
    @EventHandler
    public void playerchat(PlayerChatEvent e)
    {
        e.getPlayer().sendMessage("이 서버에서는 채팅을 칠 수 없습니다.");
        e.setCancelled(true);
    }
*/
    @EventHandler
    public void PlayerClickBlock(PlayerInteractEvent e) {

        Player p =e.getPlayer(); // 플레이어가 액션을 취했을때 플레이어 저장 (Ex: 우클릭, 좌클릭 할때 저장)

        try
        {
            if(p.getInventory().getItemInMainHand().getType()!=Material.PLAYER_HEAD)
            {
                return;
            }
                ItemStack firstitem = p.getInventory().getItemInMainHand().asOne();
                String headowner = null;
                SkullMeta skullmeta =(SkullMeta) p.getInventory().getItemInMainHand().getItemMeta();
                headowner = skullmeta.getOwner();
                if(headowner.equalsIgnoreCase("Gloden Steve Head"))
                {
                    p.removePotionEffect(PotionEffectType.SPEED);
                    p.removePotionEffect(PotionEffectType.REGENERATION);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,600, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200, 3) );
                    p.getInventory().removeItem(firstitem);
                }
                else
                {
                    p.removePotionEffect(PotionEffectType.SPEED);
                    p.removePotionEffect(PotionEffectType.REGENERATION);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100, 1));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200, 2));
                    p.getInventory().removeItem(firstitem);
                }
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            {
                e.setCancelled(true);
            }



        }
        catch(NullPointerException exception)
        {
            return;
        }

    }
    /*
    @EventHandler
    public void player(BlockBreakEvent e)
    {

        double rate;
        UUID uuid = e.getPlayer().getUniqueId();
        if(e.getBlock().getType()==Material.DIAMOND_ORE)
        {
            diamond.put(uuid,diamond.get(uuid)+1);
            rate = diamond.get(uuid)/(float)stone.get(uuid);
            if((rate*100)>6.0&&min>10&&game=="uhc")
            {
                //핵 판정
                e.getPlayer().setHealth(0);
                getServer().getPlayer("481926").sendTitle(e.getPlayer().getName(),"핵인듯");
                e.getPlayer().kick(Component.text("핵 쓰지 마세요!"));

            }
            if((rate*100)>3.0&&min>10&&game=="uhc")
            {
                //핵 판정
                getServer().getPlayer("481926").sendTitle(e.getPlayer().getName(),"핵인듯하다 아직 모름");

            }
        }
        if(e.getBlock().getType()==Material.STONE)
        {
            stone.put(uuid,stone.get(uuid)+1);
            rate = diamond.get(uuid)/(float)stone.get(uuid);
            e.getPlayer().sendTitle(Double.toString((rate*100)),"음");

        }
    }*/


    @Override
    public void onDisable()
    {
        if (isgaming)
        {
            //저장
        }

    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,String s, String[] args)
    {
        if(command.getName().equalsIgnoreCase("uhc"))
        {
            consol.sendMessage(ChatColor.AQUA+"[Hypixel] 월드 생성중");
            UHCWorldcreater();
        }
        return true;
    }



    public void config()
    {
        consol.sendMessage( ChatColor.AQUA + "[Hypixel] config파일 불러오는중");
        consol.sendMessage( ChatColor.AQUA + "[Hypixel]");
        saveConfig();
        File cfile = new File(getDataFolder(), "config.yml");
        if (cfile.length() == 0)
        {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        String[] Option = getConfig().saveToString().split("\n");
        for(int i = 0;i<Option.length;i++)
        {
            String space = "　";
            int len = Option[i].split(" : ")[0].length();

            while((16-len)>0)
            {
                space = space+"　";
                len++;
            }
            if(Option[i].split(" : ")[0].equalsIgnoreCase("평화시간"))
            {
                peacetime =Integer.parseInt(Option[i].split(" : ")[1]);
            }
            else if(Option[i].split(" : ")[0].equalsIgnoreCase("죽이는　시간"))
            {
                killtime =Integer.parseInt(Option[i].split(" : ")[1]);
            }
            else if(Option[i].split(" : ")[0].equalsIgnoreCase("마지막　시간"))
            {
                deathmatchtime =Integer.parseInt(Option[i].split(" : ")[1]);
            }
            else if(Option[i].split(" : ")[0].equalsIgnoreCase("자기장　처음　넓이"))
            {
                borderarea[0] =Integer.parseInt(Option[i].split(" : ")[1]);
            }
            else if(Option[i].split(" : ")[0].equalsIgnoreCase("자기장　두번째　넓이"))
            {
                borderarea[1] =Integer.parseInt(Option[i].split(" : ")[1]);
            }
            else if(Option[i].split(" : ")[0].equalsIgnoreCase("자기장　마지막　넓이"))
            {
                borderarea[2] =Integer.parseInt(Option[i].split(" : ")[1]);
            }
            consol.sendMessage(ChatColor.AQUA + "[Hypixel] - " + Option[i].split(":")[0]+space+" -- "+ChatColor.GREEN+Option[i].split(": ")[1]);
        }
        consol.sendMessage( ChatColor.AQUA + "[Hypixel]");
        consol.sendMessage( ChatColor.GREEN + "[Hypixel] config파일 불러옴");
    }







    public void UHCWorldcreater()
    {

            ti = 0;
            int com = 0;
            config();


            consol.sendMessage(ChatColor.WHITE + "-----------------UHC WORLD SETTING-----------------");

            Random createRandom = new Random();
            umapid = createRandom.nextInt(1000);
            mapname = "uhc" + String.valueOf(umapid);
            consol.sendMessage(ChatColor.RED + "오버월드 이름 : " + ChatColor.YELLOW + mapname + "\n");

            createRandom = new Random();
            umapid = createRandom.nextInt(1000);
            neither = "uhcne" + String.valueOf(umapid);
            consol.sendMessage(ChatColor.RED + "네더월드 이름 : " + ChatColor.YELLOW + neither + "\n");

            peacetime = Integer.valueOf(getConfig().getString("평화시간"));
            killtime = Integer.valueOf(getConfig().getString("죽이는　시간"));
            deathmatchtime = Integer.valueOf(getConfig().getString("마지막　시간"));
            consol.sendMessage(ChatColor.AQUA + "게임시간");
            consol.sendMessage(ChatColor.GREEN + "- " + String.valueOf(peacetime) + "분");
            consol.sendMessage(ChatColor.YELLOW + "-- " + String.valueOf(killtime) + "분");
            consol.sendMessage(ChatColor.RED + "--- " + String.valueOf(deathmatchtime) + "분\n");


            borderarea[0] = Integer.valueOf(getConfig().getString("자기장　처음　넓이"));
            borderarea[1] = Integer.valueOf(getConfig().getString("자기장　두번째　넓이"));
            borderarea[2] = Integer.valueOf(getConfig().getString("자기장　마지막　넓이"));
            consol.sendMessage(ChatColor.AQUA + "자기장 넓이");
            consol.sendMessage(ChatColor.GREEN + "- " + String.valueOf(borderarea[0]));
            consol.sendMessage(ChatColor.YELLOW + "-- " + String.valueOf(borderarea[1]));
            consol.sendMessage(ChatColor.RED + "--- " + String.valueOf(borderarea[2]) + "\n");


            consol.sendMessage(ChatColor.YELLOW + "월드 생성중...");
            World world;
            World neitherw;
            WorldCreator sed = new WorldCreator(neither);

            sed.environment(World.Environment.NETHER);
            neitherw = sed.createWorld();
            WorldCreator seed = new WorldCreator(mapname);
            world = seed.createWorld();
            /*
            new BukkitRunnable()
            {
                @Override
                public void run(){
                    ti++;
                    consol.sendMessage(ChatColor.YELLOW+"됨");
                    if(ti==20)
                    {
                        this.cancel();
                    }
                }
            }.runTaskTimer(this,0,20);
            while (ti==20)
            {
                int y = 0;
            }
            consol.sendMessage(ChatColor.YELLOW+"됨");
            */

                Random per = new Random();
                com = per.nextInt(4) + 68;

                consol.sendMessage(ChatColor.YELLOW + "월드 설정중...");
                world.setPVP(false);
                world.setDifficulty(Difficulty.EASY);
                //world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                world.setGameRule(GameRule.NATURAL_REGENERATION, false);
                world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

                world.getWorldBorder().setCenter(0, 0);
                world.getWorldBorder().setDamageAmount(2);
                world.getWorldBorder().setWarningDistance(100);
                world.getWorldBorder().setDamageBuffer(0);
                world.getWorldBorder().setSize(borderarea[0]);

                world.setPVP(false);
                world.setDifficulty(Difficulty.NORMAL);
                //world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                world.setGameRule(GameRule.NATURAL_REGENERATION, false);
                world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                game = "uhc";

                isdropinghead = false;
                per = new Random();
                com = per.nextInt(4) + 73;
                consol.sendMessage(ChatColor.GREEN + "월드 설정완료 (" + com + "%)");


            consol.sendMessage(ChatColor.GREEN + "월드 생성완료 (" + com + "%)");


            consol.sendMessage(ChatColor.YELLOW + "플레이어 설정중...");
            List players = Arrays.asList(Bukkit.getOnlinePlayers().toArray());
            int oneper = (100 - com) / players.size();
            for (int i = 0; i < players.size(); i++) {

                Player pl = (Player) players.get(i);

                pl.setGlowing(false);
                createRandom = new Random();
                int xi = createRandom.nextInt(Integer.valueOf(borderarea[0] / 2)) - Integer.valueOf(borderarea[0] / 4);
                int zi = createRandom.nextInt(Integer.valueOf(borderarea[0] / 2)) - Integer.valueOf(borderarea[0] / 4);

                pl.teleport(world.getHighestBlockAt(xi, zi).getLocation().add(0, 1, 0));


                pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                pl.setGameMode(GameMode.SURVIVAL);

                pl.setCompassTarget(new Location(getServer().getWorld(mapname), 0, 50, 0));
                pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 255, true));
                pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, peacetime * 1200, 255, true));

                pl.getInventory().clear();
                ItemStack firstitem = new ItemStack(Material.STONE_PICKAXE, 1);
                pl.getInventory().addItem(firstitem);

                firstitem = new ItemStack(Material.STONE_AXE, 1);
                pl.getInventory().addItem(firstitem);
                firstitem = new ItemStack(Material.STONE_SWORD, 1);
                firstitem.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
                pl.getInventory().addItem(firstitem);
                firstitem = new ItemStack(Material.STONE_SHOVEL, 1);
                pl.getInventory().addItem(firstitem);
                firstitem = new ItemStack(Material.COOKED_BEEF, 64);
                pl.getInventory().addItem(firstitem);
                consol.sendMessage(ChatColor.GREEN + "- " + pl.getName() + " (" + String.valueOf(oneper + com - 1) + "%)");
            }
            consol.sendMessage(ChatColor.GREEN + "플레이어 설정 완료 (100%)");
            consol.sendMessage(ChatColor.AQUA + "uhc 게임 시작");
            consol.sendMessage(ChatColor.WHITE + "---------------------------------------------------");
            isgaming = true;
    }


}
