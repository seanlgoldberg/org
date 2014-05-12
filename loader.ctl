load data
  infile 'c:\cygwin\home\Sean\CAMeL\ARG\Rule data\reverb_clueweb_tuples-1.1.txt'
  into table evidence
  fields terminated by "\t"
  ( id, arg1, relation, arg2, normarg1, normrelation, normarg2, junk, conf, source char(80000) )