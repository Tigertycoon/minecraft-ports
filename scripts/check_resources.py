"""Check resource JSON, 1.21 paths and the result schema of crafting recipes."""
from pathlib import Path
import json

def unique(pairs):
    result={}
    for key,value in pairs:
        if key in result: raise ValueError(f'Duplicate JSON key: {key}')
        result[key]=value
    return result

root=Path(__file__).resolve().parents[1]
paths=sorted(root.glob('mods/*/src/*/resources/**/*.json'))
for path in paths:
    data=json.loads(path.read_text(encoding='utf-8'),object_pairs_hook=unique)
    parts=path.parts
    if 'data' in parts:
        assert not any(p in parts for p in ['recipes','loot_tables','advancements']), path
        assert '/tags/items/' not in path.as_posix(), path
    if 'recipe' in parts and str(data.get('type','')).startswith('minecraft:crafting'):
        assert 'id' in data['result'] and 'item' not in data['result'], path
print(f'Validated {len(paths)} resource JSON files and Minecraft 1.21 resource paths.')
