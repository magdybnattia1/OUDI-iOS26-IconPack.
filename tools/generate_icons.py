from PIL import Image, ImageDraw, ImageFont, ImageFilter
from pathlib import Path
out=Path("app/src/main/res/drawable-nodpi"); out.mkdir(parents=True,exist_ok=True)
items={
"whatsapp":("W",(24,190,125)),"chrome":("C",(70,145,245)),"youtube":("▶",(245,55,65)),
"phone":("☎",(45,190,135)),"messages":("•••",(70,155,245)),"camera":("◉",(95,110,135)),
"settings":("⚙",(110,125,150)),"playstore":("▶",(75,155,245)),"gmail":("M",(235,75,85)),
"maps":("◆",(55,170,130)),"photos":("✿",(75,155,240)),"facebook":("f",(65,115,230)),
"instagram":("◎",(190,80,175)),"tiktok":("♪",(35,35,45)),"telegram":("➤",(45,155,225)),
"spotify":("≋",(30,175,95)),"calculator":("＋",(85,105,135)),"calendar":("21",(70,145,235)),
"clock":("◷",(75,145,225)),"files":("▤",(65,155,220)),"contacts":("●",(225,155,85)),
"drive":("△",(65,165,125)),"chatgpt":("✳",(35,165,145))}
font_path="/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
for name,(glyph,color) in items.items():
    s=192; im=Image.new("RGBA",(s,s),(0,0,0,0))
    shadow=Image.new("RGBA",(s,s),(0,0,0,0)); sd=ImageDraw.Draw(shadow)
    sd.rounded_rectangle((15,18,177,180),radius=43,fill=(5,20,45,75))
    im.alpha_composite(shadow.filter(ImageFilter.GaussianBlur(8)))
    tile=Image.new("RGBA",(s,s),(0,0,0,0)); d=ImageDraw.Draw(tile)
    d.rounded_rectangle((12,10,180,178),radius=44,fill=(*color,238),outline=(255,255,255,225),width=3)
    d.rounded_rectangle((17,15,175,89),radius=39,fill=(255,255,255,58))
    d.arc((20,18,172,172),10,170,fill=(255,255,255,135),width=3)
    im.alpha_composite(tile)
    f=ImageFont.truetype(font_path,72 if len(glyph)<2 else 53)
    box=ImageDraw.Draw(im).textbbox((0,0),glyph,font=f)
    x=(s-(box[2]-box[0]))/2-box[0]; y=(s-(box[3]-box[1]))/2-box[1]-3
    ImageDraw.Draw(im).text((x+1,y+2),glyph,font=f,fill=(0,20,40,80))
    ImageDraw.Draw(im).text((x,y),glyph,font=f,fill=(255,255,255,255))
    im.save(out/f"ic_{name}.png")
# App icon used by Android launcher
im=Image.new("RGBA",(192,192),(0,0,0,0)); d=ImageDraw.Draw(im)
d.rounded_rectangle((8,8,184,184),radius=48,fill=(35,125,220,255),outline=(255,255,255,240),width=4)
f=ImageFont.truetype(font_path,76); b=d.textbbox((0,0),"O",font=f)
d.text(((192-(b[2]-b[0]))/2,(192-(b[3]-b[1]))/2-b[1]-4),"O",font=f,fill="white")
im.save(out/"ic_oudi.png")