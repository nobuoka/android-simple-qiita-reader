Android アプリ 「Simple Qiita Reader」
========================================

アプリ全体の設計を考えるために作った小さなサンプルアプリです。

## アプリの内容

* Qiita の最近の投稿一覧を読み込んで表示する画面。
    * 最大 5 ページまで読み込みます。 リストの一番下までスクロールすることで自動で読み込まれます。
* 各投稿を表示する画面。

## アプリ全体の設計

『実践ドメイン駆動設計』 や、ここ 1、2 年ほど web 上によく書かれている 「Android アプリで DDD + MVP」
のような話や、クリーンアーキテクチャなどを参考にしています。

* レイヤ化 (下のものが上のものに依存する)
    * ドメイン層 (今回のアプリにはない)
    * アプリケーション層
    * プレゼンテーション層 (この層の中では MVP パターンを使用)
    * インフラストラクチャ層
* 外部の Web API との通信や、複数の層にまたがって使用される単純なデータ構造として DTO を Kotlin で記述。
* 依存の方向を逆にするため (インフラストラクチャ層に他の層が依存しないように、など) Dagger 2 で DI を行う。
* 非同期処理、イベント伝搬のためにアプリ全体で RxJava を使用。

### 課題

* プレゼンテーション層の MVP がまだいい感じになっていない。
    * Activity / Fragment のライフサイクルとの組み合わせ。
    * Data binding や RecyclerView 当たりとの組み合わせをどうするか。
    * もうちょっとテストを書きやすくしたい。
* DI とスコープ周りを整理して Activity / Fragment のライフサイクルに合わせたスコープを導入したい。

## 手元でのビルド

[ComponentRecyclerAdapter](https://github.com/nobuoka/ComponentsRecyclerAdapter)
という自作のライブラリを Git サブモジュールとして追加しています。
下記のように `--recursive` オプションを付けて clone するなどして、サブモジュールも clone してください。

```
git clone --recursive https://github.com/nobuoka/android-simple-qiita-reader
```

環境変数 `ANDROID_HOME` を指定すれば (必要な Android SDK コンポーネントがインストール済みであれば)
通常どおり ``./gradlew assemble` コマンドでビルドできるはずです。
Android Studio 2.1 Preview 5 で開発をしていたため、Android Studio 1.5 では開けないかもしれません。
