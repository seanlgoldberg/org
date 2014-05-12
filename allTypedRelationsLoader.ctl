load data
  infile 'c:\cygwin\home\Sean\CAMeL\ARG\sherlockrules\sherlockrules\allTypedRelations.txt'
  into table SherlockTypedRelations
  fields terminated by "\t"
  ( relation, argClass1, argClass2, weightedCount, PMIscore )