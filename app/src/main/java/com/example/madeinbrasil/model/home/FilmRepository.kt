package com.example.madeinbrasil.model.home

import com.example.madeinbrasil.model.`class`.Films

class FilmRepository {

    fun setFilms(): List<Films> {
        val filmList: MutableList<Films> = mutableListOf()

        val film1 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film2 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film3 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film4 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film5 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film6 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film7 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )
        val film8 = Films("https://media.fstatic.com/hjlXyO2Y_S03O01-K_WBSFkyvb0=/210x312/smart/media/movies/covers/2016/05/aquarius_t80043_yGfFPLM.jpg",
            "Aquarius", "Clara, 65 anos de idade, é uma escritora e crítica de música aposentada. " +
                    "Ela é viúva, mãe de três filhos adultos, e moradora de um apartamento repleto de livros e discos no Bairro de Boa Viagem," +
                    " num edifício chamado Aquarius. Interessada em construir um novo prédio no espaço, os responsáveis por uma construtora " +
                    "conseguiram adquirir quase todos os apartamentos do prédio, menos o dela. Por mais que tenha deixado bem claro que não " +
                    "pretende vendê-lo, Clara sofre todo tipo de assédio e ameaça para que mude de ideia.",
            "Drama, Suspense", "2016", "2h22min"
        )

        filmList.addAll(listOf(film1, film2, film3, film4, film5, film6, film7, film8))

        return filmList
    }
}