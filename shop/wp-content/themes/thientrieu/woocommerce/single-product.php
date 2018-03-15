<?php
/**
 * The Template for displaying all single products
 *
 * This template can be overridden by copying it to yourtheme/woocommerce/single-product.php.
 *
 * HOWEVER, on occasion WooCommerce will need to update template files and you (the theme developer).
 * will need to copy the new files to your theme to maintain compatibility. We try to do this.
 * as little as possible, but it does happen. When this occurs the version of the template file will.
 * be bumped and the readme will list any important changes.
 *
 * @see 	    http://docs.woothemes.com/document/template-structure/
 * @author 		WooThemes
 * @package 	WooCommerce/Templates
 * @version     1.6.4
 */

if ( ! defined( 'ABSPATH' ) ) {
	exit; // Exit if accessed directly
}

get_header( 'shop' ); ?>
<div id="content">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-9">
				<div class="bot-ct-lf">
					<div class="tm-news">
						<?php while ( have_posts() ) : the_post(); ?>

							<?php wc_get_template_part( 'content', 'single-product' ); ?>

						<?php endwhile; // end of the loop. ?>
					</div>
					<div class="sociall">
			            <div class="fb-comments" data-href="<?php the_permalink();?>" data-width="100%"></div>
			        </div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-3">
				<div class="bot-ct-rg">
					<?php do_action( 'woocommerce_sidebar' ); ?>
				</div> <!-- end bot-ct-rg -->
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<?php get_footer( 'shop' ); ?>
