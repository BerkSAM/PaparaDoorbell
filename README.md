# Papara Doorbell (Recipara)

Papara Doorbell (Recipara), kullanıcıların çeşitli tarifleri keşfetmelerine ve favori tariflerini kaydetmelerine olanak sağlayan bir Android uygulamasıdır. Uygulama, Spoonacular API'yi kullanarak tarif verilerini alır ve bu verileri kullanıcıya sunar.

## Özellikler

- **Tarif Keşfetme**: Uygulama, kullanıcılara çeşitli tarifler sunar. Kullanıcılar, tarifleri kategoriye göre filtreleyebilirler.
- **Tarif Detayları**: Kullanıcılar, bir tarifin detaylarını görüntüleyebilirler. Bu detaylar arasında malzemeler, hazırlık süresi ve porsiyon sayısı bulunur.
- **Favori Tarifler**: Kullanıcılar, beğendikleri tarifleri favorilere ekleyebilirler. Favori tarifler, daha sonra hızlı ve kolay bir şekilde erişilebilir.
- **Bildirimler**: Uygulama, yeni tarifler geldiğinde kullanıcıya bildirim gönderir.

## Kurulum

1. Projeyi GitHub'dan klonlayın veya indirin.
2. Android Studio'da projeyi açın.
3. `local.properties` dosyasına Spoonacular API anahtarınızı ekleyin: `spoonacularApiKey="YOUR_API_KEY"`(Bu madde bootcamp projesinden dolayı geçersizdir. Uygulamayı inceleme amaçlı.)
4. Run 'app' komutunu kullanarak uygulamayı çalıştırın.

## Kullanılan Teknolojiler

- Kotlin
- Android Jetpack Compose
- Hilt (Dependency Injection)
- Retrofit - OkHttp (Network requests)
- Coil (Image loading)
- Room (Local database)
- Coroutines (Asynchronous programming)
- ViewModel - UI State Management
- JUnit and Mockito (Unit testing)

## Mimari

Uygulama, modern Android uygulama geliştirme tekniklerini ve en iyi uygulamaları kullanarak oluşturulmuştur. Uygulamanın mimarisi, aşağıdaki bileşenleri içerir:

- **ViewModel**: UI ile ilgili verilerin saklanmasından ve yönetilmesinden sorumludur. ViewModel, UI'nin durumunu yönetir ve UI'nin durumunu değiştirebilecek işlemleri gerçekleştirir.
- **Repository**: Uygulamanın veri kaynaklarına erişim sağlar. Repository, verileri çeşitli kaynaklardan alır ve bu verileri ViewModel'e sağlar.
- **Room Database**: Uygulamanın yerel veri kaynağıdır. Room, SQLite üzerine inşa edilmiştir ve verilerin yerel olarak saklanmasını ve alınmasını sağlar.
- **Retrofit**: Uygulamanın ağ veri kaynağıdır. Retrofit, HTTP API'leri ile etkileşim kurmak için kullanılır.
- **Coil**: Coil, Kotlin için hafif bir görüntü yükleme kütüphanesidir. Coil, görüntüleri yüklemek ve önbelleğe almak için kullanılır.

## Testler

Uygulama, unit test ile test edilmiştir. Testler, JUnit ve Mockito kullanılarak yazılmıştır.

## Projeden Görüntüler
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/6098a279-c319-4d02-84e9-769fce427148" alt="UygulamaIcon" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/c642e5d1-7d45-477b-a82d-e0a0777c2cca" alt="LottieScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/af83d05f-784a-46ba-93d2-f3ab033a9deb" alt="HomaScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/ca81837b-dbed-4e4e-87dc-9d8e4cea96bf" alt="RecipeScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/7a39ec58-a5e6-498e-8de6-ab100ed57ffd" alt="RecipeScreen2" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/87f989ff-97a7-419c-88a4-72e1af0b1bf6" alt="RecipeDetailScreen1" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/05e59ae2-3f5c-4197-8a68-09909fc565cd" alt="RecipeDetailScreen2" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/1aca53fe-cdee-40db-ac4d-e37f0a6ef969" alt="FavoriteScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/95813514-f218-4a5c-92fd-0e08d13f3411" alt="FavoriteExtendScreen" width="300"/>
