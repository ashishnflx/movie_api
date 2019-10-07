export inpath=$1
export outpath=$2
echo "Preprocessing $inpath/titles.tsv"
grep '\t2017\t' $inpath/titles.tsv | grep -E 'movie|tvSeries|tvEpisode' | cut -d$'\t' -f1-3 > $outpath/titles_filter.tsv || exit $?
echo "Processed and stored to $outpath/titles_filter.tsv"

echo "Preprocessing $inpath/cast.tsv"
cut -d$'\t' -f1-2 $inpath/cast.tsv > $outpath/cast_filter.tsv || exit $?
echo "Processed and stored to $outpath/cast_filter.tsv"

echo "Preprocessing $inpath/titles_cast.tsv"
grep 'actor' $inpath/titles_cast.tsv | cut -d$'\t' -f1,3 > $outpath/titles_cast_filter.tsv || exit $?
echo "Processed and stored to $outpath/titles_cast_filter.tsv"

echo "Preprocessing not needed $inpath/titles_episode.tsv"
cp -f $inpath/titles_episode.tsv $outpath/titles_episode_filter.tsv || exit $?
echo "Copied to $outpath/titles_episode_filter.tsv"

echo "Preprocessing $inpath/titles_ratings.tsv"
cut -d$'\t' -f1-2 $inpath/titles_ratings.tsv > $outpath/titles_ratings_filter.tsv || exit $?
echo "Processed and stored to $outpath/titles_ratings_filter.tsv"

