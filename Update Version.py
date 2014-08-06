import json
import re

def updatefile(filename, ver):
	lines = open(filename).readlines()

	for i, line in enumerate(lines):
		if 'public static final String VER = "' in line:
			lines[i] = '	public static final String VER = "%s";\n' % newVer
	
	open(filename, 'w').write(''.join(lines))


newVer = raw_input('Version number: ')

modinfo = json.load(open('mcmod.info'))

modinfo[0]['version'] = newVer

json.dump(modinfo, open('mcmod.info', 'w'), indent = 4)

updatefile('mmdanggg2\\doge\\DogeInfo.java', newVer)
