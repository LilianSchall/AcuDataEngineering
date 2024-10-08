const getRegionIds = () => {
  return {
    regions: [
      {
        id: 1,
        name: "Auvergne-Rhône-Alpes",
      },
      {
        id: 2,
        name: "Bourgogne-Franche-Comté",
      },
      {
        id: 3,
        name: "Bretagne",
      },
      {
        id: 4,
        name: "Centre-Val de Loire",
      },
      {
        id: 5,
        name: "Grand Est",
      },
      {
        id: 6,
        name: "Hauts-de-France",
      },
      {
        id: 7,
        name: "Ile-de-France",
      },
      {
        id: 8,
        name: "Normandie",
      },
      {
        id: 9,
        name: "Nouvelle-Aquitaine",
      },
      {
        id: 10,
        name: "Occitanie",
      },
      {
        id: 11,
        name: "Pays de la Loire",
      },
      {
        id: 12,
        name: "Provence-Alpes-Côte d'Azur",
      },
    ],
  }['regions'];
};

const getRegionAverageScore = (regionId) => {
  return {
    average_score: [
      {
        id_exercise: 1,
        score: 50,
      },
      {
        id_exercise: 2,
        score: 60,
      },
      {
        id_exercise: 3,
        score: 70,
      },
      {
        id_exercise: 4,
        score: 80,
      },
      {
        id_exercise: 5,
        score: 90,
      },
    ],
  }['average_score'];
};

const getExerciseIds = () => {
  return {
    exercises: [
      {
        name: "80cols",
        difficulty: "easy",
        id: 1,
      },
      {
        name: "80cols_grep",
        difficulty: "easy",
        id: 2,
      },
      {
        name: "add_int_ptr",
        difficulty: "easy",
        id: 3,
      },
      {
        name: "alphabet",
        difficulty: "easy",
        id: 4,
      },
      {
        name: "alphanum",
        difficulty: "easy",
        id: 5,
      },
      {
        name: "array_max_min",
        difficulty: "easy",
        id: 6,
      },
      {
        name: "ascii_carousel",
        difficulty: "easy",
        id: 7,
      },
      {
        name: "ascii_house",
        difficulty: "easy",
        id: 8,
      },
      {
        name: "assignment_operator",
        difficulty: "easy",
        id: 9,
      },
      {
        name: "binary_cipher",
        difficulty: "easy",
        id: 10,
      },
      {
        name: "binary_search",
        difficulty: "easy",
        id: 11,
      },
      {
        name: "binary_search_ptr",
        difficulty: "easy",
        id: 12,
      },
      {
        name: "binary_tree_dynamic",
        difficulty: "easy",
        id: 13,
      },
      {
        name: "bit_rotation",
        difficulty: "easy",
        id: 14,
      },
      {
        name: "bubble_sort",
        difficulty: "easy",
        id: 15,
      },
      {
        name: "check_alphabet",
        difficulty: "easy",
        id: 16,
      },
      {
        name: "clang-format",
        difficulty: "easy",
        id: 17,
      },
      {
        name: "connect4",
        difficulty: "easy",
        id: 18,
      },
      {
        name: "create_array",
        difficulty: "easy",
        id: 19,
      },
      {
        name: "create_files",
        difficulty: "easy",
        id: 20,
      },
      {
        name: "cut_csv",
        difficulty: "easy",
        id: 21,
      },
      {
        name: "date_format",
        difficulty: "easy",
        id: 22,
      },
      {
        name: "digit",
        difficulty: "easy",
        id: 23,
      },
      {
        name: "display_square",
        difficulty: "easy",
        id: 24,
      },
      {
        name: "dlist",
        difficulty: "easy",
        id: 25,
      },
      {
        name: "element_count",
        difficulty: "easy",
        id: 26,
      },
      {
        name: "fact",
        difficulty: "easy",
        id: 27,
      },
      {
        name: "fact_iter",
        difficulty: "easy",
        id: 28,
      },
      {
        name: "facto",
        difficulty: "easy",
        id: 29,
      },
      {
        name: "fibo",
        difficulty: "easy",
        id: 30,
      },
      {
        name: "fibo_iter",
        difficulty: "easy",
        id: 31,
      },
      {
        name: "fifo",
        difficulty: "easy",
        id: 32,
      },
      {
        name: "file_basics_insert_line",
        difficulty: "easy",
        id: 33,
      },
      {
        name: "file_basics_merge_files",
        difficulty: "easy",
        id: 34,
      },
      {
        name: "find_ascii",
        difficulty: "easy",
        id: 35,
      },
      {
        name: "free_array",
        difficulty: "easy",
        id: 36,
      },
      {
        name: "freq_analysis",
        difficulty: "easy",
        id: 37,
      },
      {
        name: "functional_programming",
        difficulty: "easy",
        id: 38,
      },
      {
        name: "generate_files",
        difficulty: "easy",
        id: 39,
      },
      {
        name: "generic_void_list",
        difficulty: "easy",
        id: 40,
      },
      {
        name: "get_int_value",
        difficulty: "easy",
        id: 41,
      },
      {
        name: "glob_easy",
        difficulty: "easy",
        id: 42,
      },
      {
        name: "glob_remove_shell",
        difficulty: "easy",
        id: 43,
      },
      {
        name: "grade",
        difficulty: "easy",
        id: 44,
      },
      {
        name: "greatest_divisor",
        difficulty: "easy",
        id: 45,
      },
      {
        name: "hacker_news",
        difficulty: "easy",
        id: 46,
      },
      {
        name: "handling_complex",
        difficulty: "easy",
        id: 47,
      },
      {
        name: "hanoi",
        difficulty: "easy",
        id: 48,
      },
      {
        name: "hash_map",
        difficulty: "easy",
        id: 49,
      },
      {
        name: "hello_friends",
        difficulty: "easy",
        id: 50,
      },
      {
        name: "hello_world",
        difficulty: "easy",
        id: 51,
      },
      {
        name: "hello_world_shebang",
        difficulty: "easy",
        id: 52,
      },
      {
        name: "highest_common_factor",
        difficulty: "easy",
        id: 53,
      },
      {
        name: "hill_array",
        difficulty: "easy",
        id: 54,
      },
      {
        name: "insertion_sort",
        difficulty: "easy",
        id: 55,
      },
      {
        name: "inside",
        difficulty: "easy",
        id: 56,
      },
      {
        name: "inside_noif",
        difficulty: "easy",
        id: 57,
      },
      {
        name: "int_mats_mult",
        difficulty: "easy",
        id: 58,
      },
      {
        name: "int_palindrome",
        difficulty: "easy",
        id: 59,
      },
      {
        name: "int_sqrt",
        difficulty: "easy",
        id: 60,
      },
      {
        name: "int_vector_insert_sort",
        difficulty: "easy",
        id: 61,
      },
      {
        name: "int_vector_max",
        difficulty: "easy",
        id: 62,
      },
      {
        name: "levenshtein",
        difficulty: "easy",
        id: 63,
      },
      {
        name: "lookup_table",
        difficulty: "easy",
        id: 64,
      },
      {
        name: "my_abs",
        difficulty: "easy",
        id: 65,
      },
      {
        name: "my_atoi",
        difficulty: "easy",
        id: 66,
      },
      {
        name: "my_bc",
        difficulty: "easy",
        id: 67,
      },
      {
        name: "my_c_tail",
        difficulty: "easy",
        id: 68,
      },
      {
        name: "my_calloc",
        difficulty: "easy",
        id: 69,
      },
      {
        name: "my_file",
        difficulty: "easy",
        id: 70,
      },
      {
        name: "my_first_variable",
        difficulty: "easy",
        id: 71,
      },
      {
        name: "my_free",
        difficulty: "easy",
        id: 72,
      },
      {
        name: "my_itoa",
        difficulty: "easy",
        id: 73,
      },
      {
        name: "my_itoa_base",
        difficulty: "easy",
        id: 74,
      },
      {
        name: "my_malloc",
        difficulty: "easy",
        id: 75,
      },
      {
        name: "my_memmove",
        difficulty: "easy",
        id: 76,
      },
      {
        name: "my_pow",
        difficulty: "easy",
        id: 77,
      },
      {
        name: "my_recycler",
        difficulty: "easy",
        id: 78,
      },
      {
        name: "my_round",
        difficulty: "easy",
        id: 79,
      },
      {
        name: "my_strcasecmp",
        difficulty: "easy",
        id: 80,
      },
      {
        name: "my_strcmp",
        difficulty: "easy",
        id: 81,
      },
      {
        name: "my_strcpy",
        difficulty: "easy",
        id: 82,
      },
      {
        name: "my_strdup",
        difficulty: "easy",
        id: 83,
      },
      {
        name: "my_strlen",
        difficulty: "easy",
        id: 84,
      },
      {
        name: "my_strlowcase",
        difficulty: "easy",
        id: 85,
      },
      {
        name: "my_strndup",
        difficulty: "easy",
        id: 86,
      },
      {
        name: "my_strspn",
        difficulty: "easy",
        id: 87,
      },
      {
        name: "my_strstr",
        difficulty: "easy",
        id: 88,
      },
      {
        name: "my_strtok_r",
        difficulty: "easy",
        id: 89,
      },
      {
        name: "my_strupcase",
        difficulty: "easy",
        id: 90,
      },
      {
        name: "null_terminated_arrays",
        difficulty: "easy",
        id: 91,
      },
      {
        name: "number_digits_rec",
        difficulty: "easy",
        id: 92,
      },
      {
        name: "palindrome",
        difficulty: "easy",
        id: 93,
      },
      {
        name: "pi_generator",
        difficulty: "easy",
        id: 94,
      },
      {
        name: "pine",
        difficulty: "easy",
        id: 95,
      },
      {
        name: "pointer_swap",
        difficulty: "easy",
        id: 96,
      },
      {
        name: "print_arguments",
        difficulty: "easy",
        id: 97,
      },
      {
        name: "prototypes",
        difficulty: "easy",
        id: 98,
      },
      {
        name: "quick_sort",
        difficulty: "easy",
        id: 99,
      },
      {
        name: "read_and_inc",
        difficulty: "easy",
        id: 100,
      },
      {
        name: "repeat",
        difficulty: "easy",
        id: 101,
      },
      {
        name: "right_tarball",
        difficulty: "easy",
        id: 102,
      },
      {
        name: "rotx",
        difficulty: "easy",
        id: 103,
      },
      {
        name: "run_length_encoding",
        difficulty: "easy",
        id: 104,
      },
      {
        name: "sed_trailing_whitespaces",
        difficulty: "easy",
        id: 105,
      },
      {
        name: "selection_sort",
        difficulty: "easy",
        id: 106,
      },
      {
        name: "seq",
        difficulty: "easy",
        id: 107,
      },
      {
        name: "sieve_eratosthenes_advanced",
        difficulty: "easy",
        id: 108,
      },
      {
        name: "spoilers",
        difficulty: "easy",
        id: 109,
      },
      {
        name: "stack",
        difficulty: "easy",
        id: 110,
      },
      {
        name: "str_revert",
        difficulty: "easy",
        id: 111,
      },
      {
        name: "string_replace",
        difficulty: "easy",
        id: 112,
      },
      {
        name: "test_a_bit",
        difficulty: "easy",
        id: 113,
      },
      {
        name: "tinylibstream",
        difficulty: "easy",
        id: 114,
      },
      {
        name: "user_ids",
        difficulty: "easy",
        id: 115,
      },
      {
        name: "using_special_variables",
        difficulty: "easy",
        id: 116,
      },
      {
        name: "variant",
        difficulty: "easy",
        id: 117,
      },
      {
        name: "vector",
        difficulty: "easy",
        id: 118,
      },
    ],
  }['exercises'];
};

const getExerciseAverageScore = (exerciseId) => {
  return {
    average_score: [
      {
        id_region: 1,
        score: 40,
      },
      {
        id_region: 2,
        score: 50,
      },
      {
        id_region: 3,
        score: 60,
      },
      {
        id_region: 4,
        score: 20,
      },
      {
        id_region: 5,
        score: 70,
      },
      {
        id_region: 6,
        score: 80,
      },
      {
        id_region: 7,
        score: 75,
      },
      {
        id_region: 8,
        score: 30,
      },
      {
        id_region: 9,
        score: 45,
      },
      {
        id_region: 10,
        score: 55,
      },
      {
        id_region: 11,
        score: 65,
      },
      {
        id_region: 12,
        score: 35,
      },
    ],
  }['average_score'];
};

const getExerciseNbAlerts = (exerciseId) => {
  return {
    'nb_alert': [
      {
        'id_region': 1,
        'nb_alert': 10
      },
      {
        'id_region': 2,
        'nb_alert': 20
      },
      {
        'id_region': 3,
        'nb_alert': 30
      },
      {
        'id_region': 4,
        'nb_alert': 40
      },
      {
        'id_region': 5,
        'nb_alert': 50
      },
      {
        'id_region': 6,
        'nb_alert': 60
      },
      {
        'id_region': 7,
        'nb_alert': 70
      },
      {
        'id_region': 8,
        'nb_alert': 80
      },
      {
        'id_region': 9,
        'nb_alert': 90
      },
      {
        'id_region': 10,
        'nb_alert': 100
      },
      {
        'id_region': 11,
        'nb_alert': 110
      },
      {
        'id_region': 12,
        'nb_alert': 120
      },
    ]
  }['nb_alert'];
}

const getRegionNbAlerts = (regionId) => {
  return {
    'nb_alert': [
      {
        'id_exercise': 1,
        'nb_alert': 10
      },
      {
        'id_exercise': 2,
        'nb_alert': 20
      },
      {
        'id_exercise': 3,
        'nb_alert': 30
      },
      {
        'id_exercise': 4,
        'nb_alert': 40
      },
      {
        'id_exercise': 5,
        'nb_alert': 50
      }
    ]
  }['nb_alert'];
}

export {
    getRegionIds,
    getRegionAverageScore,
    getExerciseIds,
    getExerciseNbAlerts,
    getExerciseAverageScore,
    getRegionNbAlerts,
}