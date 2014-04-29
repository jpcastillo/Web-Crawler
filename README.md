web-search-engine
=================

Introduction to Information Retrieval

Example of use:
[user@server]./run.sh string_seed_file int_num_pages int_hops_away string_output_dir


Part A.
Build a Web Crawler for edu pages

Your application should read a file of seed .edu URLs and crawl the .edu pages.

The application should also input the number of pages to crawl and the number of levels (hops (i.e. hyperlinks) away from the seed URLs).

All crawled pages (html files) should be stored in a folder.

We recommend using Java, which is the language that we will use in the discussion sections. If you use another language, you cannot expect to get any support from the TA if you get stuck. You should not use any crawler package, since the purpose of the project is to see some of the challenges involved in building a crawler.

You will be graded on the correctness and efficiency of your crawler (e.g., how does it handle duplicate pages? Or is the crawler multi-threaded?).

You should collect at least 5 GB of data.


Part B.
Build index and Web-based search interface

Write a program that uses the Lucene libraries to index all the html files in the folder you created in Part A. Handle different fields like title, body, creation date (if available).


The interface should contain a textbox, and a search button. When you click search, you should see a list of results (e.g., first 10) returned by Lucene for this query and their scores.  The list should be ordered in decreasing order of score. Handle fields as you deem appropriate. For Twitter, order by a combination of time and relevance; describe your ranking function.

We recommend using Java Server Pages (JSP), which is the only Web-based language we will discuss in the discussion sections.

Do not use SOLR or another framework that automatically builds the UI for you.

