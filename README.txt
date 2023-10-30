Udało się wykonać wszystkie zadania (włącznie z zadaniem dodatkowym) za łącznie 11 punktów.

Aplikacja po włączeniu powinana uruchomić activity z dwoma przyciskami "Lista produktów" i "Opcje".

W activity "Opcje" (OptionsActivity) przygotowano dwie opcje zmiany związane z aplikacją - możliwość zmiany rozmiaru czcionki i koloru tła. Do przechowania stanu wykorzystano SharedPreferences.

W activity "Lista Produktów" (plik ProductListActivity), zaimplementowano dodawanie, usuwanie i edycję produktów z listy zapisywanej w bazie danych (utworzenie bazy danych w pliku AppDatabase, a DAO w pliku ProductDao). Activity to jest podzielone na dwie części, formularz w górnej części odpowiada za dodawanie nowych produktów do listy. Na dole prezentowana jest lista dodanych już produktów. Lista jest edytowalna tzn. po kliknięciu w pole np nazwy produktu możemy je edytować. Aby usunąć produkt z listy należy wcisnąć przycisk "x", aby zaznaczyć/odznaczyć go jako kupiony należy wcisnąć checkbox po prawej stronie.

W pliku ProductContentProvider utworzono Content Providera. Był on testowany z wykorzystaniem narzędzia "adb" z SDK Platform-Tools. Przykładowe komendy to:

Wyświetlenie listy produktów: 
adb shell content query --uri content://com.example.mini_projekt_1.provider/products

Usunięcie wszystkich produktów:
adb shell content delete --uri content://com.example.mini_projekt_1.provider/products



