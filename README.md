# kartaca-software-development-task

## Başlangıç

Projeyi çalıştırmak için öncelikle Docker'ın yüklü olması gerekmektedir.
bu proje usgs web sitesinden bir günlük depren verilerini json formatında alır ve gelen get isteğine bu verileri işleyip (lat lon ve location) json formatında gönderir
"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2024-01-01&endtime=2024-01-02"
projede flink kullanılmamıştır 
database yoktur 
büyük veri işleme  ve canlı veri akışı görevi tamamlanamamıştır
frontend ise json verilerini alır ve harita üzerinde gösterir


### Kurulum

Öncelikle backend ve frontend Docker konteynerlerini oluşturmak için aşağıdaki adımları izleyin:

1. Terminal veya komut istemcisinde proje dizinine gidin.

#### Backend

```bash
cd backend
docker build -t backend -f Dockerfile .
docker run -d -p 8080:8080 backend
```
2. Terminal veya komut istemcisinde proje dizinine gidin.
#### Frontend

```bash
cd frontend 
docker build -t frontend -f Dockerfile .
docker run -d -p 3000:80 frontend
```

#### Görüntüleme

Web tarayıcınızı açın ve `localhost:3000` adresine istek atın.