<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<meta name="SKYPE_TOOLBAR" content="SKYPE_TOOLBAR_PARSER_COMPATIBLE" />
	<meta content="telephone=no" name="format-detection" />
	<link rel="shortcut icon" href="" type="" />
	<link href='https://fonts.googleapis.com/css?family=Roboto:300,400,700,500&subset=latin,vietnamese,latin-ext' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="<?php bloginfo('template_directory' ); ?>/css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="<?php bloginfo('template_directory' ); ?>/font-awesome/css/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" href="<?php bloginfo('template_directory' ); ?>/css/slick.css"/>
	<link rel="stylesheet" type="text/css" href="<?php bloginfo('template_directory' ); ?>/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<?php bloginfo('template_directory' ); ?>/css/format.css"/>
	<?php wp_head(); ?>
</head>
<body <?php body_class(); ?>>
	<?php global $hk_options; ?>
	<div id="wrapper">
		<div id="header">
			<div class="header-top">
				<div class="container">
					<p class="pull-left"><?php echo $hk_options[texttop]; ?></p>
					<div class="pull-right">
						<ul>
							<li><a href="<?php bloginfo('url'); ?>/gioi-thieu">Giới thiệu</a></li>
							<li><a href="<?php bloginfo('url'); ?>/tai-khoan">Tài khoản</a></li>
							<li><a href="<?php bloginfo('url'); ?>/gio-hang">Giỏ hàng</a></li>
							<li><a href="<?php bloginfo('url'); ?>/lien-he">Liên hệ</a></li>
						</ul>	
					</div>
				</div>
			</div> <!-- end header-top -->
			<div class="header-content">
				<div class="container">
					<div class="row">
						<div class="col-md-3 col-sm-12 col-xs-12 logo">
							<a href="<?php bloginfo('url'); ?>"><img src="<?php echo $hk_options[logo][url]; ?>" alt="<?php bloginfo('name'); ?>"/></a>
						</div>
						<div class="col-md-6 col-sm-6 col-xs-12 giohang text-right">
							<div class="search-box">
								<form action="<?php bloginfo('url' ); ?>/" method="GET" role="form">
									<input type="hidden" name="post_type" value="product">

									<div class="row">
										<div class="col-xs-12 col-sm-3 col-md-3 no-padding">
											<select name="product_cat" class="form-control">
												<option class="boldoption" value="">Lựa chọn</option>
												<?php $args = array( 
												    'hide_empty' => 0,
												    'taxonomy' => 'product_cat',
												    'orderby' => id,
												    ); 
												    $categories = get_categories( $args ); 
												    foreach ( $categories as $category ) { ?>
												    <?php $id = $category->term_id; ?>
												    <option value="<?php echo $category->slug; ?>"><?php echo $category->name; ?></option>	
												<?php } ?>
											</select>
										</div>
										<div class="col-xs-12 col-sm-9 col-md-9 no-padding">
											<input type="text" name="s" class="form-control" placeholder="Nhập từ khóa tìm kiếm">
											<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12 giohang text-right">
							<p>Giỏ hàng: <?php echo sprintf (_n( '%d Sản phẩm', '%d Sản phẩm', WC()->cart->cart_contents_count ), WC()->cart->cart_contents_count ); ?> - <?php echo WC()->cart->get_cart_total(); ?></p>
						</div>	
					</div>
				</div>
			</div> <!-- end header-content -->
			<div class="header-bot">
				<div class="container">
					<div class="row">
						<div class="col-md-12 col-sm-8 col-xs-8">
							<div class="menu">
								<?php wp_nav_menu( array( 'theme_location' => 'main_nav', 'container' => 'false', 'menu_id' => 'main-nav', 'menu_class' => 'block-menu') ); ?>
							</div>
						</div>
					</div>
				</div>
			</div> <!-- end header-bot -->
		</div> <!-- end header -->
		<?php if(!is_home()) { ?>
		<div class="bread">
			<div class="container"><?php if ( function_exists('yoast_breadcrumb') ) {yoast_breadcrumb('<p id="breadcrumbs"><i class="fa fa-home"></i> ','</p>');} ?></div>
		</div>
		<?php } ?>