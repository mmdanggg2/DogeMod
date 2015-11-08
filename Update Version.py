import re

def updateinfo(filename, ver):
	lines = open(filename).readlines()
	
	for i, line in enumerate(lines):
		if 'public static final String VER = "' in line:
			lines[i] = '	public static final String VER = "%s";\n' % newVer
	
	open(filename, 'w').write(''.join(lines))

def updatebuild(filename, ver):
	lines = open(filename).readlines()
	
	for i, line in enumerate(lines):
		if 'version = "' in line:
			if not line.startswith('    '):
				lines[i] = 'version = "%s"\n' % newVer
	
	open(filename, 'w').write(''.join(lines))

newVer = raw_input('Version number: ')

updateinfo('src\\main\\java\\mmdanggg2\\doge\\DogeInfo.java', newVer)
updatebuild('build.gradle', newVer)
