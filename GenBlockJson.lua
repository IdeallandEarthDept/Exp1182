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

-- for i = 1, 30 do
--     GenSP(i)
-- end
GenBlock("flame_wall_no_adv");
-- GenItemNake("balloon");
-- GenItemNake("feather");
-- GenItemNake("shoes");
-- GenItemNake("food");
-- GenItemNake("flame_wall_no_adv");
--GenBlock("test_block");
--GenBlock("popolon_door");
--GenBlock("aphrodite_door");
