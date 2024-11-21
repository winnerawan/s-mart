#!/usr/bin/env python3

import sys
import xlrd
import json

# Note.
# process.py [filename] [sheetname] [start] [finish] [row_date] [col_date]
#
# Xlrd.
# comment code at file compdoc.py at line 425
# comment code at file compdoc.py at line 426
# comment code at file compdoc.py at line 427

filename = sys.argv[1]
sheetname = sys.argv[2]
start = int(sys.argv[3])
finish = int(sys.argv[4])

wb = xlrd.open_workbook(filename)
sn = wb.sheet_by_name(sheetname)
data = [];

for rownum in range(start, min(finish, sn.nrows)):
    data.append(sn.row_values(rownum))

print(json.dumps({
	"data":data
}))