# Set this to the root of your project when deployed:
http_path = "/"
css_dir = "."
sass_dir = "css"
images_dir = "images"
javascripts_dir = "."
add_import_path "./frameworks/bourbon/dist"
add_import_path "./frameworks/neat-master/app/assets/stylesheets"
add_import_path "./frameworks/sassy-buttons-0.2.6/stylesheets"

# You can select your preferred output style here (can be overridden via the command line):
# output_style = :expanded or :nested or :compact or :compressed
# output_style = :compressed

# To enable relative paths to assets via compass helper functions. Uncomment:
# relative_assets = true

# To disable debugging comments that display the original location of your selectors. Uncomment:
# line_comments = false
line_comments = false

# If you prefer the indented syntax, you might want to regenerate this
# project again passing --syntax sass, or you can uncomment this:
# preferred_syntax = :sass
# and then run:
# sass-convert -R --from scss --to sass sass scss && rm -rf sass && mv scss sass
