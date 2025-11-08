import argparse
import sys
from typing import BinaryIO


class WrapperGenerator:

    def __init__(self, fin, fout, wrapped_class):
        self._fin: BinaryIO = fin
        self._fout: BinaryIO  = fout
        self._wrapped_class = wrapped_class

    def generate(self):
        while line := str(self._fin.readline()):
            print(line)
            first_space_pos = line.find(' ')
            return_type = line[:first_space_pos]
            method_decl = line[first_space_pos+1:line.rfind(';')]
            method_name = method_decl[:method_decl.find('(')]

            is_void = return_type == 'void'

            params_raw = method_decl[method_decl.find('(') + 1:method_decl.rfind(')')].split(', ')

            if not params_raw or params_raw[0] == '':
                params = ''
            else:
                params_names = list(map(lambda x: x.split(' ')[1], params_raw))
                params = ', '.join(params_names)

            if is_void:
                self._fout.write(
                    '@Override\npublic void %s {\n    %s.%s(%s);\n}\n\n' % (
                        method_decl,
                        wrapped_class,
                        method_name,
                        params,
                    )
                ) 
            else:
                self._fout.write(
                    '@Override\npublic %s %s {\n    return %s.%s(%s);\n}\n\n' % (
                        return_type,
                        method_decl,
                        wrapped_class,
                        method_name,
                        params,
                    )
                ) 


if __name__ == '__main__':  # noqa: C901

    parser = argparse.ArgumentParser(
        prog='generate_wrapper',
        description='Konna lib wrapper generator (only method bodies, for LWJGL modules)',
        epilog='By Akira Nihil'
    )

    parser.add_argument('-i', '--in', required=True)
    parser.add_argument('-o', '--out')
    parser.add_argument('-c', '--class', required=True)

    args = parser.parse_args()

    in_file = getattr(args, 'in')
    out_file = getattr(args, 'out')
    wrapped_class = getattr(args, 'class')

    if not wrapped_class:
        print("Error: no wrapper class is specified")
        sys.exit(1)

    if not out_file:
        out_file = in_file + f'_{wrapped_class}_wrapper.java'

    

    with open(in_file, 'r', encoding='utf8') as fin, open(out_file, 'w+', encoding='utf8') as fout:
        gen = WrapperGenerator(fin, fout, wrapped_class)
        try:
            gen.generate()
            print(f"Wrapper method bodies has been written to {out_file}")
            sys.exit(0)
        except Exception as e:
            print(e)
            sys.exit(1)
