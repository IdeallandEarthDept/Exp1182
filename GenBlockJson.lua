local outFile = nil;
local modName = "exp1182";
local blockName = "";

local function GenModelBlockItem()
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
	outFile:write('{\n');
	local content = string.format("\t\"parent\": \"%s:block/%s\"\n", modName,blockName );
	outFile:write(content);
	outFile:write('}\n');
	outFile:close();
end

local function GenModelBlock()
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\block\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
	outFile:write('{\n');
	outFile:write('\t\"parent\": \"block/cube_all\",\n');
	outFile:write('\t\"textures\": {\n');
	local content = string.format("\t\t\"all\": \"%s:block/%s\"\n", modName,blockName );
	outFile:write(content);
	outFile:write('\t}\n');
	outFile:write('}\n');
	outFile:close();
end

local function GenBlockState()
	local path = string.format("src\\main\\resources\\assets\\%s\\blockstates\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
	outFile:write('{\n');
	outFile:write('\t\"variants\": {\n');
	local content = string.format("\t\t\"\": { \"model\": \"%s:block/%s\" }\n", modName,blockName );
	outFile:write(content);
	outFile:write('\t}\n');
	outFile:write('}\n');
	outFile:close();
end

local function GenBlock(_blockName)
	blockName = _blockName;
	print("Creating:"..blockName)
	GenModelBlockItem();
	GenModelBlock();
	GenBlockState();
end

local function GenItem(_typeName, _itemName)
	print("Creating:".._typeName.." ".._itemName)
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, _itemName);
	outFile = io.open(path,"w");

	local content = string.format('{"parent": "item/handheld","textures": {"layer0":"%s:item/%s/%s"}}\n', modName, _typeName, _itemName );
	outFile:write(content);

	outFile:close();
end

local function GenItemNake(_itemName)
	print("Creating:".._itemName)
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, _itemName);
	outFile = io.open(path,"w");

	local content = string.format('{"parent": "item/handheld","textures": {"layer0":"%s:item/%s"}}\n', modName, _itemName );
	outFile:write(content);

	outFile:close();
end

local function GenTCG(_rarity, _itemName)
	print("Creating TCG:".._rarity.." ".._itemName)
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, "tcg_".._itemName);
	outFile = io.open(path,"w");

	local content = string.format('{"parent": "item/handheld","textures": {"layer0":"%s:tcg/tcgbg_%s","layer1":"%s:tcg/%s"}}\n', modName, _rarity, modName, _itemName );
	outFile:write(content);

	outFile:close();
end

local function GenSP(index)
	print("Creating:SP_"..index)

	local regName_prefix = "box_"

	--item
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, regName_prefix..index);
	outFile = io.open(path,"w");

	local content = string.format('{"parent": "backtones:block/mjds_box"}');
	outFile:write(content);

	outFile:close();

	--block
	path = string.format("src\\main\\resources\\assets\\%s\\blockstates\\%s.json", modName, regName_prefix..index);
	outFile = io.open(path,"w");

	content = string.format('{\n		"variants": {\n		"": { "model": "backtones:block/mjds_box" }\n	}\n	}');
	outFile:write(content);

	outFile:close();
end

--for i = 1, 4 do
--	GenBlock("mjds_bgp"..i);
--end


--GenBlock("cleanse_sword");
-- GenItemNake("balloon");
-- GenItemNake("feather");
-- GenItemNake("shoes");
-- GenItemNake("food");
-- GenItemNake("shield_bronze");
-- GenItemNake("great_key");
-- GenItemNake("candle");
-- GenItemNake("holy_water");
-- GenItemNake("breakable");
-- GenItemNake("triangle");

--GenItem("misc", "boots");
--GenItem("misc", "devil_wing");
--GenItem("misc", "jar");
--GenItem("misc", "lamp");
--GenItem("misc", "sabre");
--GenItem("misc", "salt");
--GenItem("misc", "great_key_1");
--GenItem("misc", "spawner_egg_slime");
--GenItem("misc", "map");

--for i = 1, 4 do
--  GenItem("armor", "popolon_armor_"..i);
--  GenItem("armor", "aphrodite_armor_"..i);
--end
--GenItem("misc", "alterego_2");
--GenItem("tool", "ceramic_bow");
--GenItem("tool", "murasama");
--GenItem("food", "pure_water");
--GenItem("food", "mantle");

--GenItem("armor", "obsidian_helmet");
--GenItem("armor", "obsidian_chestplate");
--GenItem("armor", "obsidian_leggings");
--GenItem("armor", "obsidian_boots");
--GenBlock("test_block");
--GenBlock("popolon_door");
--GenBlock("aphrodite_door");

--GenBlock("trade_mongo_sword");
--GenBlock("castle_bg_r");
--GenBlock("athena_body");
--GenBlock("athena_head");
--GenBlock("old_body");
--GenBlock("old_head");
--GenBlock("forest_floor");

--GenItem("misc", "quiver");
--GenBlock("castle_bg");
--GenBlock("castle_floor");

--
--for i = 0, 7 do
--	GenTCG("c",i);
--end
--
--for i = 8, 11 do
--	GenTCG("u",i);
--end
--
--for i = 12, 16 do
--	if (i == 14) then
--		GenTCG("u",i);
--	else
--		GenTCG("r",i);
--	end
--end
--
--for i = 17, 18 do
--	GenTCG("m",i);
--end

--GenItem("misc", "hell_coin_base");
--GenTCG("c","0");
--GenBlock("rally_helper");

--GenBlock("trap_flame_2");
--GenBlock("trap_flame_3");
--GenBlock("trap_spike");
--GenBlock("trap_spike_2");
--GenBlock("trap_spike_3");
--GenBlock("trap_spike_poison");


for i = 0, 15 do
	--GenBlock("dungeon_brick_"..i);
	--GenBlock("adj_lock_on");
end
--GenBlock("flesh_block_1");
--GenBlock("flesh_block_brain");
--GenBlock("flesh_block_eye");
--GenBlock("flesh_block_mouth");

-- GenItem("misc", "nano_mender_greater");
-- GenItem("misc", "package_fade_armor_diamond");
-- for i = 1,4 do
-- 	GenItem("misc", "armor_qsh_"..i);
-- end

-- for i = 1,4 do
-- 	GenItem("misc", "mor_armor_"..i);
-- end

--for i = 0,15 do
--	GenBlock("skyland_runestone_"..i);
--end

--GenBlock("skyland_pebble");
--GenBlock("block_loop_16");
--GenBlock("random_tp");

--GenItem("food", "float_food")
--GenItem("misc", "cycle_stone_shard")
--GenItem("misc", "disturb_measure")
--GenItem("skill", "skill_radar_creature")
--GenItem("food", "chilli")
--GenItem("food", "digest_pills")

-- for i = 1,4 do
-- 	GenItem("armor", "l_m_armor_" .. i)
-- end

--//{
--	"type": "item",
--	"name": "minecraft:golden_apple",
--	"weight": 20
--},
