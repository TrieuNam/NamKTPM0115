<?php get_header(); ?>
<div class="content">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-9">
				<div class="main-category">
					<h1 class="title"><span><?php single_cat_title(); ?></span></h1>
					<div class="content-cat">
						<?php if (have_posts()) : ?>
						<?php while (have_posts()) : the_post(); ?>
							<div class="list">
								<div class="row">
									<div class="col-xs-12 col-sm-3 col-md-3">
										<div class="img-post">
											<a href="<?php the_permalink(); ?>"><?php show_thumb(370,257,1,'c'); ?></a>
										</div>
									</div>
									<div class="col-xs-12 col-sm-9 col-md-9">
										<div class="info-cat">
											<h4><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h4>
											<p><?php echo teaser(60); ?></p>
											<div class="meta">
												<span><i class="fa fa-user" aria-hidden="true"></i> <?php the_author(); ?></span>
												<span><i class="fa fa-calendar-minus-o" aria-hidden="true"></i> <?php echo get_the_date('d/m/Y') ?></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						<?php endwhile; else : ?>
						<p class="error">Không có bài viết trong chuyên mục</p>
						<?php endif; ?>
					</div>
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
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-3">
				<div class="sidebar">
					<?php get_sidebar(); ?>
				</div>
			</div>
		</div>
	</div>
</div>
<?php get_footer(); ?>