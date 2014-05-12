load data
  infile 'c:\Users\Sean\Downloads\eclipse\workspace\SherlockAnalysis\linkingtypedrelations.txt'
  into table SherlockLinkingTypedRelations
  fields terminated by "\t"
  ( relation, argClass1, argClass2, weightedCount, PMIscore )