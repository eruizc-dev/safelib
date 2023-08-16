{ pkgs ? import <nixpkgs> {} }:
pkgs.mkShell {
	buildInputs = with pkgs; [
		gradle_8
		jdk19
		jdt-language-server
	];
}
