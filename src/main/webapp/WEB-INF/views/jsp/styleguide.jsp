<!doctype html>
<html lang="en">
<%@ include file="includes/head.jsp"%>
<body style="background-color: #ACACA8;">
	
	<%@ include file="includes/header.jsp"%>

	<!-- *************************************************** -->
	<!-- *********************  FONTS  ********************* -->
	<!-- *************************************************** -->
	<div class="row">
		<div class="sample-container">
			<div class="header-container col-4">
				<h1>login: ${loginDetail.logged}</h1>
				<h2>Header 2</h2>
				<h3>Header 3</h3>
				<h4>Header 4</h4>
				<h5>Header 5</h5>
				<h6>Header 6</h6>
			</div>
			<div class="col-4">
				<article>
					<header>
				        <h1>Título del artículo</h1>
				        <div class="authoring">
				            <address class="author">By <a rel="author" href="/author/john-doe">John Doe</a></address> 
				            <time pubdate datetime="2011-08-28" title="August 28th, 2011">28/12/1986</time>
				        </div>
				    </header>
				    <div class="content">
				    	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque et tincidunt arcu. Sed pulvinar sodales urna et pharetra. Curabitur tincidunt massa ac nisi laoreet tincidunt. Nam quis suscipit tellus. Maecenas nec dictum erat. Nunc id nunc sapien. Pellentesque accumsan metus non tellus semper, ut commodo est sollicitudin. Praesent at erat laoreet, aliquet ipsum et, scelerisque purus. Sed laoreet sit amet tortor a tempor.</p>
				    </div>
				</article>
			</div>
			<div class="col-4">
				<div class="row">
					<div class="cloud-tag">
						<span class="tag" data-size="10">tag-1</span>
						<span class="tag" data-size="20">tag-2</span>
						<span class="tag" data-size="30">tag-3</span>
						<span class="alert-text tag" data-size="40">tag-4</span>
						<span class="tag" data-size="50">tag-5</span>
						<span class="tag" data-size="60">tag-6</span>
						<span class="tag" data-size="70">tag-7</span>
						<span class="tag" data-size="80">tag-8</span>
						<span class="alert-text tag" data-size="90">tag-9</span>
					</div>
				</div>
				<div class="row">
					<article class="post">
					<header>
				        <div class="stars" data-mark="1.5"></div>
				        <div class="authoring">
				            <address class="author">By <a rel="author" href="/user/john-doe">John Doe</a></address> 
				            <time pubdate datetime="1986-12-28" title="December 28th, 1986">28/12/1986</time>
				        </div>
				    </header>
				    <div class="content">
				    	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque et tincidunt arcu. Sed pulvinar sodales urna et pharetra. Curabitur tincidunt massa ac nisi laoreet tincidunt. Nam quis suscipit tellus. Maecenas nec dictum erat. Nunc id nunc sapien. Pellentesque accumsan metus non tellus semper, ut commodo est sollicitudin. Praesent at erat laoreet, aliquet ipsum et, scelerisque purus. Sed laoreet sit amet tortor a tempor.</p>
				    </div>
				</article>
				</div>
			</div>
		</div>
	</div>

	<!-- *************************************************** -->
	<!-- *********************  FORMS  ********************* -->
	<!-- *************************************************** -->
	<div class="row">
		<div class="sample-container">
			<div class="col-4">
				<form action="">
					<fieldset>
						<legend>Long fields</legend>
						<div class="field-container">
							<input required id="username" type="text" class="username">
							<label for="text">username</label>
						</div>
						<div class="field-container">
							<input required id="password" type="password">
							<label for="password">password</label>
						</div>
						<div class="field-container">
							<input required id="email" type="email">
							<label for="email">email</label>
						</div>
						<div class="field-container">
							<input id="search" type="search">
							<label for="search">search</label>
						</div>
						<div class="field-container invalid">
							<input required id="url" type="url">
							<label for="url">url</label>
						</div>
						<input id="date" type="date">	
						<input id="week" type="week">	
						<input id="range" type="range">
						<input id="time" type="time">	
					</fieldset>
				</form>
			</div>
			<div class="col-4">
				<form action="">
					<fieldset>
						<legend>Short fields</legend>
						<div class="row">
							<div class="row"><input type="radio" name="radios" id="radio1" value="radio1"><label for="radio1">Option 1</label></div>
							<div class="row"><input type="radio" name="radios" id="radio2" value="radio2"><label for="radio2">Option 2</label></div>
							<div class="row"><input type="radio" name="radios" id="radio3" value="radio3"><label for="radio3">Option 3</label></div>
							<div class="row"><input type="radio" name="radios" id="radio4" value="radio4"><label for="radio4">Option 4</label></div>
							<div class="row"><input type="radio" name="radios" id="radio5" value="radio5"><label for="radio5">Option 5</label></div>
						</div>
						<div class="row">
							<div class="row"><input type="checkbox" id="checkbox01" name="checkbox01"><label for="checkbox01"></label></div>
							<div class="row"><input type="checkbox" id="checkbox02" name="checkbox02"><label for="checkbox02">CheckBox 02</label></div>
						</div>
						<div class="row">
							<div class="select-mask">
								<select name="select1" id="select1">
									<option value="option1">Option 1</option>
									<option value="option2">Option 2</option>
									<option value="option3">Option 3</option>
									<option value="option4">Option 4</option>
									<option value="option5">Option 5</option>
								</select>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="col-4">
				<form action="">
					<fieldset>
						<legend>Buttons</legend>
						<div class="row">
							<div class="col-4"><input type="reset"  value="Reset"></div>
							<div class="off-4 col-4"><div class="spinner h1" data-spinner="&#xe028;"></div></div>
						</div>
						<div class="row">
							<div class="col-4"><input type="submit" value="Send"></div>
							<div class="off-4 col-4"><div class="spinner h2" data-spinner="&#xe028;"></div></div>
						</div>
						<div class="row">
							<div class="col-4"><input type="button" value="Button"></div>
							<div class="off-4 col-4"><div class="spinner h3" data-spinner="&#xe028;"></div></div>
						</div>
						<div class="row">
							<div class="col-4 button-pill-glass">Button2</div>
							<div class="off-3 col-4"><div class="spinner h3" data-spinner="&#xe028;"></div></div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<!-- *************************************************** -->
	<!-- *****************  LIST & QUOTES  ***************** -->
	<!-- *************************************************** -->
	<div class="row">
		<div class="sample-container">
			<div class="col-3">
				<ol>
					<li><p>list item 1</p>
						<ol>
							<li><p>list intem 2-1
								<ol>
									<li><p>list item 3-1</p></li>
									<li><p>list item 3-2</p></li>
								</ol>
							</li>
							<li><p>list intem 2-2</p></li>
							<li><p>list intem 2-3</p></li>
						</ol>
					</li>
					<li><p>list item 2</p></li>
					<li><p>list item 3</p></li>
				</ol>
			</div>
			<div class="col-3">
				<ul>
					<li><p>list item 1</p>
						<ul>
							<li><p>list intem 2-1
								<ul>
									<li><p>list item 3-1</p></li>
									<li><p>list item 3-2</p></li>
								</ul>
							</li>
							<li><p>list intem 2-2</p></li>
							<li><p>list intem 2-3</p></li>
						</ul>
					</li>
					<li><p>list item 2</p></li>
					<li><p>list item 3</p></li>
				</ul>
			</div>
			<div class="col-3">
				<ul class="dropable">
					<li class="dropdown">
						<checkbox id="li1"></checkbox>
						<label for="li1">List Item 1</label>
						<ul>
							<li><p>List item 1-1</p></li>
							<li><p>List item 1-2</p></li>
							<li><p>List item 1-3</p></li>
						</ul>
					</li>
					<li><p>List Item 2</p></li>
					<li><p>List Item 3</p></li>
				</ul>
			</div>
			<div class="col-3">
				<blockquote>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque et tincidunt arcu. Sed pulvinar sodales urna et pharetra. Curabitur tincidunt massa ac nisi laoreet tincidunt. <blockquote><p>Nam quis suscipit tellus. Maecenas nec dictum erat.</p></blockquote> Nunc id nunc sapien.</p>
				</blockquote>
			</div>
		</div>
	</div>

	<!-- *************************************************** -->
	<!-- *****************  TABLES & POST  ***************** -->
	<!-- *************************************************** -->
	<div class="row">
		<div class="sample-container">
			<div class="col-4">
				<div class="table-wrapper">
					<table>
						<theader>Table Header</theader>
						<tr class="trheader">
							<th class="sortable">col 1
								<ul class="sorting-options">
									<li data-icon="&#xe032;" onclick="sortDesc(1)">mayor a menor</li>
									<li data-icon="&#xe033;" onclick="sortAlph(1)">alfabeticamente</li>
								</ul>
							</th>
							<th class="sortable">col 2
								<ul class="sorting-options">
									<li data-icon="&#xe032;" onclick="sortDesc(2)">mayor a menor</li>
									<li data-icon="&#xe033;" onclick="sortAlph(2)">alfabeticamente</li>
								</ul>
							</th>
							<th class="">col 3</th>
							<th class="sortable">col 4
								<ul class="sorting-options">
									<li data-icon="&#xe032;" onclick="sortDesc(4)">mayor a menor</li>
									<li data-icon="&#xe033;" onclick="sortAlph(4)">alfabeticamente</li>
								</ul>
							</th>
						</tr>
						<tr>
							<td>row 1, col 1</td>
							<td>row 1, col 2</td>
							<td>row 1, col 3</td>
							<td>row 1, col 4</td>
						</tr>
						<tr>
							<td>row 2, col 1</td>
							<td>row 2, col 2</td>
							<td>row 2, col 3</td>
							<td>row 2, col 4</td>
						</tr>
						<tr>
							<td>row 3, col 1</td>
							<td>row 3, col 2</td>
							<td>row 3, col 3</td>
							<td>row 3, col 4</td>
						</tr>
						<tr>
							<td>row 4, col 1</td>
							<td>row 4, col 2</td>
							<td>row 4, col 3</td>
							<td>row 4, col 4</td>
						</tr>
						<tr>
							<td>row 5, col 1</td>
							<td>row 5, col 2</td>
							<td>row 5, col 3</td>
							<td>row 5, col 4</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="col-4">				
				<div class="table-wrapper-cart">
					<div class="row cart-header">
						<span class="cart-empty">Check Out</span>
						<span class="delete"></span>
					</div>
					<div id="cart-slider" class="scroll-y">
						<div class="scrollbar right"><div class="track"><div class="thumb"></div></div></div>	
						<div class="viewport left">
							<div class="overview cart-y-container">					
								<table class="">
									<tr class="trheader">
										<th class="sortable">Item								
											<ul class="sorting-options">
												<li data-icon="&#xe032;" onclick="sortDesc(1)">mayor a menor</li>
												<li data-icon="&#xe033;" onclick="sortAlph(1)">alfabeticamente</li>
											</ul>
										</th>
										<th>€</th><th></th><th></th>
									</tr>
									<tr>
										<td class="item-name">Metatrón 500 Turbocontroller</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 2</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 3</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 4</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-4">				
				<div class="table-wrapper-cart">
					<div class="row cart-header">
						<span class="cart-empty">Check Out</span>
						<span class="delete"></span>
					</div>
					<div id="cart-slider" class="scroll-y">
						<div class="scrollbar right"><div class="track"><div class="thumb"></div></div></div>	
						<div class="viewport left">
							<div class="overview cart-y-container">					
								<table class="">
									<tr class="trheader">
										<th class="sortable">Item								
											<ul class="sorting-options">
												<li data-icon="&#xe032;" onclick="sortDesc(1)">mayor a menor</li>
												<li data-icon="&#xe033;" onclick="sortAlph(1)">alfabeticamente</li>
											</ul>
										</th>
										<th>€</th><th></th><th></th>
									</tr>
									<tr>
										<td class="item-name">Metatrón 500 Turbocontroller</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 2</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 3</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 4</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>

									<tr>
										<td class="item-name">Item 5</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>						
									<tr>
										<td class="item-name">Item 6</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 7</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 8</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 9</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
									<tr>
										<td class="item-name">Item 10</td>
										<td class="item-price">1200</td>
										<td><span class="editable"></span></td>
										<td><span class="removable"></span></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- *************************************************** -->
	<!-- ********************  SLIDERS  ******************** -->
	<!-- *************************************************** -->
	<div class="row">
		<div class="sample-container">
			<div class="col-4">
				  <div class="slider" data-delay="3000">
				    <div class="ctrl_next">&#xe038;</div>
				    <div class="ctrl_prev">&#xe039;</div>
				    <div class="ctrl_play">&#xe036;</div>
				    <div class="slides-container">
				      <div class="slide" style="text-align: center; background: white;">SLIDE 1</div>
				      <div class="slide" style="text-align: center; background: #aaa;">SLIDE 2</div>
				      <div class="slide" style="text-align: center; background: white;">SLIDE 3</div>
				      <div class="slide" style="text-align: center; background: #aaa;">SLIDE 4</div>
				    </div>  
				  </div>
			</div>
			<div class="col-4">
				<div id="news1" class="scroll-y">
					<div class="scrollbar left"><div class="track"><div class="thumb"></div></div></div>
					<div class="viewport right">
						<div class="overview news-y-container">
							<div class="new-y">NEW - 1</div>
							<div class="new-y">NEW - 2</div>
							<div class="new-y">NEW - 3</div>
							<div class="new-y">NEW - 4</div>
							<div class="new-y">NEW - 5</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-4">
				<div id="news2" class="scroll-x">
					<div class="scrollbar left"><div class="track"><div class="thumb"></div></div></div>
					<div class="viewport left">
						<div class="overview news-x-container">
							<div class="new-x">NEW - 1</div>
							<div class="new-x">NEW - 2</div>
							<div class="new-x">NEW - 3</div>
							<div class="new-x">NEW - 4</div>
							<div class="new-x">NEW - 5</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="includes/scripts.jsp"%>

</body>
</html>