export inpath=$1
export outpath=$2
echo "Preprocessing $inpath/title.basics.tsv"
grep '\t2017\t' $inpath/title.basics.tsv | grep -E 'movie|tvSeries|tvEpisode' | cut -d$'\t' -f1-3 > $outpath/titles_filter.tsv || exit $?
echo "Processed and stored to $outpath/titles_filter.tsv"

echo "Preprocessing $inpath/name.basics.tsv"
cut -d$'\t' -f1-2 $inpath/name.basics.tsv > $outpath/cast_filter.tsv || exit $?
echo "Processed and stored to $outpath/cast_filter.tsv"

echo "Preprocessing $inpath/title.principals.tsv"
grep 'actor' $inpath/title.principals.tsv | cut -d$'\t' -f1,3 > $outpath/titles_cast_filter.tsv || exit $?
echo "Processed and stored to $outpath/titles_cast_filter.tsv"

echo "Preprocessing not needed $inpath/title.episode.tsv.tsv"
cp -f $inpath/title.episode.tsv $outpath/titles_episode_filter.tsv || exit $?
echo "Copied to $outpath/titles_episode_filter.tsv"

echo "Preprocessing $inpath/title.ratings.tsv"
cut -d$'\t' -f1-2 $inpath/title.ratings.tsv > $outpath/titles_ratings_filter.tsv || exit $?
echo "Processed and stored to $outpath/titles_ratings_filter.tsv"

