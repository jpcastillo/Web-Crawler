#!/bin/bash

###
# CS172 Information Retrieval
# John Castillo && Kevin Mitton
# BASH script for processing input to be fed into web crawler
###

NUM_ARGS=4
ISNUM='^[0-9]+$'
EXITARGS=10
EXITFILE=11
EXITNUM=12
EXITDIR=13

# Do we have a correct number of arguements?
if [[ $# -ne $NUM_ARGS ]]; then
	echo -e "Error: Invalid number of arguements. Expected four.\
	\nSample usage:\
	\n\t$0 <seed-file:seed.txt> <num-pages: 10000> <hops-away: 6> <output-dir>";
	exit $EXITARGS;
else

	# Is the seed_file arguement?
	if [[ -f $1 && -s $1 ]]; then
		seed_file=$1;
	else
		echo "Error: '$1' is not a file or is an empty file.";
		exit $EXITFILE;
	fi

	# Is num_pages an integer?
	if [[ $2 =~ $ISNUM ]]; then
		num_pages=$2;
	else
		echo "Error: Second arguement expected integer got '$2'";
		exit $EXITNUM;
	fi

	# Is hops_away an integer?
	if [[ $3 =~ $ISNUM ]]; then
		hops_away=$3
	else
		echo "Error: Third arguement expected integer got '$3'";
		exit $EXITNUM;
	fi

	# Is output_dir a directory?
	if [[ -d $4 ]]; then
		output_dir=$4
	else
		echo -e "Warning: Output directory does not exist.\nDo you want directory '$4' to be created?";
		select yn in "Yes" "No"; do
			case $yn in
				Yes ) mkdir -p "$4"; output_dir=$4; break;;
				No ) echo "Error: No output directory. Exiting now."; exit $EXITDIR;;
			esac
		done
	fi
fi

#echo "Time to run crawler binary with the below arguements!!!";
#echo -e "Seed File: $seed_file\nNumber of Pages: $num_pages\nNumber of Hops: $hops_away\nOutput Diectory: $output_dir";
exec java Main $seed_file $num_pages $hops_away $output_dir
