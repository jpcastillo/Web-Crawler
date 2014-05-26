Java Web Crawler
=================

UCR Introduction to Information Retrieval CS172

Example of use:
[user@server]./run.sh string_seed_file int_num_pages int_hops_away string_output_dir

This web crawler is multi-threaded. I use a pooled approach for creating and managing threads. I implemented an HTML parser for retrieving links given in HTML 'a' tags' href attribute. I also accounted for the use of relative paths in these href attribute. I check the top level domain to filter out non-edu pages. This was my first time writing in Java so I had to pick it up quickly. I also wrote a neat bash script for running processing the command line arguements and creating any directories needed. This project was developed on UNIX and tested on UNIX and Linux.

Below is a description of the requirements and information for this project:

--------------------
Part A.
Build a Web Crawler for edu pages.

Your application should read a file of seed .edu URLs and crawl the .edu pages.

The application should also input the number of pages to crawl and the number of levels (hops (i.e. hyperlinks) away from the seed URLs).

All crawled pages (html files) should be stored in a folder.

We recommend using Java, which is the language that we will use in the discussion sections. If you use another language, you cannot expect to get any support from the TA if you get stuck. You should not use any crawler package, since the purpose of the project is to see some of the challenges involved in building a crawler.

You will be graded on the correctness and efficiency of your crawler (e.g., how does it handle duplicate pages? Or is the crawler multi-threaded?). You should collect at least 5 GB of data.
--------------------
