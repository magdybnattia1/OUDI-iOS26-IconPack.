from pathlib import Path
from urllib.request import urlopen, Request
from PIL import Image, ImageDraw, ImageFont, ImageEnhance
from io import BytesIO

BASE = "https://raw.githubusercontent.com/SysAdminDoc/iOSIconPack/master/app/src/main/res/drawable-xxxhdpi/"
OUT = Path("app/src/main/res/drawable-nodpi")
OUT.mkdir(parents=True, exist_ok=True)
ICONS = {
 "whatsapp":"ios26_lg_whatsapp.png","chrome":"ios26_lg_chrome.png","youtube":"ios26_lg_youtube.png",
 "phone":"ios26_lg_phone.png","messages":"ios26_lg_messages.png","camera":"ios26_lg_camera.png",
 "settings":"ios26_lg_settings.png","playstore":"ios26_lg_appstore.png","gmail":"ios26_lg_gmail.png",
 "maps":"ios26_lg_google_maps.png","photos":"ios26_lg_google_photos.png","facebook":"ios26_lg_facebook.png",
 "instagram":"ios26_lg_instagram.png","tiktok":"ios26_lg_tiktok.png","telegram":"ios26_lg_telegram.png",
 "spotify":"ios26_lg_spotify.png","calculator":"ios26_lg_calculator.png","calendar":"ios26_lg_calendar.png",
 "clock":"ios26_lg_clock.png","files":"ios26_lg_files.png","contacts":"ios26_lg_phone.png",
 "drive":"ios26_lg_google_drive.png","chatgpt":"ios26_lg_notes.png"
}
for name, filename in ICONS.items():
 request = Request(BASE+filename, headers={"User-Agent":"OUDI-iOS26-IconPack/1.0"})
 with urlopen(request, timeout=30) as response: image=Image.open(BytesIO(response.read())).convert("RGBA")
 image=image.resize((192,192),Image.Resampling.LANCZOS)
 alpha=image.getchannel("A"); rgb=image.convert("RGB")
 rgb=ImageEnhance.Color(rgb).enhance(1.65)
 rgb=ImageEnhance.Contrast(rgb).enhance(1.18)
 rgb=ImageEnhance.Brightness(rgb).enhance(1.06)
 rgb.putalpha(alpha); rgb.save(OUT/f"ic_{name}.png",optimize=True)
font_path="/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
image=Image.new("RGBA",(192,192),(0,0,0,0)); draw=ImageDraw.Draw(image)
draw.rounded_rectangle((8,8,184,184),radius=48,fill=(28,112,215,255),outline=(255,255,255,240),width=4)
font=ImageFont.truetype(font_path,78); box=draw.textbbox((0,0),"O",font=font)
draw.text(((192-(box[2]-box[0]))/2,(192-(box[3]-box[1]))/2-box[1]-4),"O",font=font,fill="white")
image.save(OUT/"ic_oudi.png",optimize=True)
print(f"Prepared {len(ICONS)} vivid iOS 26 Liquid Glass icons.")
