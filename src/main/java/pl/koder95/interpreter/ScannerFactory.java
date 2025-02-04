package pl.koder95.interpreter;

import java.util.Scanner;

/**
 * Fabryka {@link Scanner skanerów}.
 * Implementacja pozwala na określenie dowolnych właściwości skanera przed jego użyciem,
 * takich jak {@link Scanner#delimiter() znak delimitera}. Domyślna implementacja tworzy
 * skaner z domyślnymi właściwościami.
 */
public interface ScannerFactory {

    /**
     * Tworzy nową instancję skanera dostosowaną do konkretnych zastosowań.
     *
     * @param readable dane wejściowe możliwe do odczytania przez interfejs {@link Readable}
     * @return nowa instancja {@link Scanner skanera}
     */
    default Scanner create(Readable readable) {
        return new Scanner(readable);
    }
}
