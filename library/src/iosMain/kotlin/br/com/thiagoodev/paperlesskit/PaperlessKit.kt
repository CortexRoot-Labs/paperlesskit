package br.com.thiagoodev.paperlesskit

actual class PaperlessKit : PaperlessKitInterface {
    actual override suspend fun scan(options: Options): PDF {
        TODO("Not yet implemented")
    }

    actual override suspend fun pick(): PDF {
        TODO("Not yet implemented")
    }

    actual override suspend fun open(pdf: PDF) {
        TODO("Not yet implemented")
    }

    actual override suspend fun open(path: String) {
        TODO("Not yet implemented")
    }

}