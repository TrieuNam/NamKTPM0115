<?php
/**
 * The Template for displaying product archives, including the main shop page which is a post type archive
 *
 * This template can be overridden by copying it to yourtheme/woocommerce/archive-product.php.
 *
 * HOWEVER, on occasion WooCommerce will need to update template files and you (the theme developer).
 * will need to copy the new files to your theme to maintain compatibility. We try to do this.
 * as little as possible, but it does happen. When this occurs the version of the template file will.
 * be bumped and the readme will list any important changes.
 *
 * @see 	    http://docs.woothemes.com/document/template-structure/
 * @author 		WooThemes
 * @package 	WooCommerce/Templates
 * @version     2.0.0
 */

if ( ! defined( 'ABSPATH' ) ) {
	exit; // Exit if accessed directly
}
get_header( 'shop' ); ?>
<div id="content">
	<div class="tm-bot-ct">
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-9">
					<div class="block-product">
						<div class="tm-news">
							<?php if ( apply_filters( 'woocommerce_show_page_title', true ) ) : ?>
								<h1 class="title">
									<span><?php woocommerce_page_title(); ?></span>
								</h1>
							<?php endif; ?>
							<?php
								/**
								 * woocommerce_archive_description hook.
								 *
								 * @hooked woocommerce_taxonomy_archive_description - 10
								 * @hooked woocommerce_product_archive_description - 10
								 */
								do_action( 'woocommerce_archive_description' );
							?>

							<?php if ( have_posts() ) : ?>

								<?php
									/**
									 * woocommerce_before_shop_loop hook.
									 *
									 * @hooked woocommerce_result_count - 20
									 * @hooked woocommerce_catalog_ordering - 30
									 */
									do_action( 'woocommerce_before_shop_loop' );
								?>
								<div class="clear"></div>
								<?php woocommerce_product_loop_start(); ?>

									<?php woocommerce_product_subcategories(); ?>

									<?php while ( have_posts() ) : the_post(); ?>

										<?php global $product; ?>
										<li class="col-md-4 col-sm-6 col-xs-6">
											<div class="detail-product">
												<div class="post-img">
												<a href="<?php the_permalink(); ?>" class="img"><?php show_thumb(270,300,1,'c'); ?></a>
												<form action="" method="post">
												<input type="hidden" name="add-to-cart" value="<?php the_id(); ?>">
												<button class="addc"><i class="fa fa-cart-plus" aria-hidden="true"></i> Thêm vào giỏ</button>
												</form>
												<a class="viewm" href="<?php the_permalink(); ?>"><i class="fa fa-search-plus" aria-hidden="true"></i> Chi tiết </a>
												</div>
												<h4><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h4>
												<div class="score-callback" data-score="4"></div>
												<p><span class="price-pro"><?php echo $product->get_price_html(); ?></span></p>
												<?php if($product->sale_price) { ?>
												<div class="sale">
													<?php  $percentage = round( ( ( $product->regular_price - $product->sale_price ) / $product->regular_price ) * 100 );
														   echo $price . sprintf( __('%s', 'woocommerce' ), $percentage . '%' ); ?>
												</div>
												<?php } ?>
											</div>
										</li>
									<?php endwhile; // end of the loop. ?>

								<?php woocommerce_product_loop_end(); ?>
								<?php if(paginate_links()!='') {?>
								<div class="quatrang">
									<?php
									global $wp_query;
									$big = 999999999;
									echo paginate_links( array(
										'base' => str_replace( $big, '%#%', esc_url( get_pagenum_link( $big ) ) ),
										'format' => '?paged=%#%',
										'prev_text'    => __('«'),
										'next_text'    => __('»'),
										'current' => max( 1, get_query_var('paged') ),
										'total' => $wp_query->max_num_pages
										) );
								    ?>
								</div>
								<?php } ?>
								<?php elseif ( ! woocommerce_product_subcategories( array( 'before' => woocommerce_product_loop_start( false ), 'after' => woocommerce_product_loop_end( false ) ) ) ) : ?>

									<?php wc_get_template( 'loop/no-products-found.php' ); ?>

								<?php endif; ?>
							</div>
						</div>
					</div> <!-- end bot-ct-lf -->
					<div class="col-xs-12 col-sm-12 col-md-3 bot-ct-rg">
						<?php
							/**
							 * woocommerce_sidebar hook.
							 *
							 * @hooked woocommerce_get_sidebar - 10
							 */
							do_action( 'woocommerce_sidebar' );
						?>
					</div> <!-- end bot-ct-rg -->
			</div>
			<div class="clear"></div>
		</div>
	</div> <!-- end tm-bot-ct -->
</div> <!-- end content -->
<?php get_footer( 'shop' ); ?>
