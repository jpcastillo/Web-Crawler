<!DOCTYPE HTML>
<html>
	<head>
		<title>Computer Science Club | Pasadena City College</title>
		<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico">
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<script src="/js/jquery-1.8.3.min.js"></script>
		<script src="/js/topmenu.js"></script>
		<script src="/js/autorun.js"></script>
		<link rel="stylesheet" href="/css/mainstyle.css" />
		<link rel="stylesheet" href="/css/topmenu.css" />
		<link rel="stylesheet" href="/css/leftmenu.css" />
		<noscript>
			<p>site will suck without javascript</p>
		</noscript>

		<script>
		
        $(document).ready(function()
        {
        	$('#leftmenu > ul > li').each( function() {
        		$(this).bind({
        			click: function ()
        			{
        				//alert( $(this).children('a').attr('href') );
        				window.location.href=$(this).children('a').attr('href');
        			},
        			hover: function ()
        			{
        				$(this).css('cursor','crosshair');
        			}
        		});
        	});
		});

		</script>
	</head>

	<body class="home-page">

		<div id="header-wrapper">
			
<div id="topnavi">
	<div id="topmenu" class="topmenu-default">
		<ul>
			<li><a href="/">home</a></li>
			<li><a href="/about.php">about</a></li>
			<li><a href="/contact.php">contact</a></li>
			<li><a href="/apply">apply</a></li>
			<li><a href="/members">members</a></li>
			
		</ul>
	</div><!-- end topmenu -->
</div><!-- end topnavi -->
		</div><!-- end header-wrapper -->

		<div id="main-wrapper">

			<div class="left">
				<div id="sidebar">
					<img src="/images/cscpcc_logo_alpha.png" width="250" />
				</div><!-- end sidebar -->

				<div id="sidebar">
					<!-- hello world! this is my box of links. -->
					
<div id="leftmenu">
	<ul>
		<li><a href="/apply/" id="apply" title="apply now">On-line Application Form</a></li>
		<li><a href="/dl/?f=membershipform.pdf" title="apply later">Application Form PDF</a></li>
		<li><a href="/feedback/" title="feedback">Send us feedback</a></li>
		<li><a href="/members/" title="member login">members panel</a></li>
		<li><a href="/admin/" title="admin login">administrative panel</a></li>
		<li><a href="/info/?s=meeting" title="meeting info">Meeting information</a></li>
		<li><a href="/info/?s=special" title="events info">Special event information</a></li>
	</ul>
</div><!-- end leftmenu -->
				</div><!-- end sidebar -->
			</div><!-- end .left -->

			<div id="content">
				Welcome to the Pasadena City College Computer Science Club Home Page.				<p>
					The club is a community of students coming together to explore computer science, 
					not just on an academic level, but also on an interpersonal level. 
					Come see what all the hubbub is about. We meet on campus, Thursdays 
					at 12:00 PM in room R211.
				</p>
				<p>
					Come stop by. We have a Rasberry Pi.
				</p>
				<p>
					I'd like to give a <a href="specialthanks.php">special thanks</a> to those 
					who helped put together this website. Thank you.
				</p>
				<p>
					-- Dimitiri Pierre-Louis
				</p>
			</div><!-- end content -->

			<div class="clear"></div>

			<div id="footer-wrapper">
				
<div id="footer-content">
	<div align="center">
		<a href="/">home</a> | <a href="/about.php">about</a> | <a href="/contact.php">contact</a> | <a href="/members/">members</a>
		<br /><br />Copyright &copy; <span id="copy_yr">1987</span> Computer Science Club, Pasadena City College
	</div>
</div><!-- end footer-content -->
			</div><!-- end footer-wrapper -->
	
		</div><!-- end main-wrapper -->
	</body>

</html>

